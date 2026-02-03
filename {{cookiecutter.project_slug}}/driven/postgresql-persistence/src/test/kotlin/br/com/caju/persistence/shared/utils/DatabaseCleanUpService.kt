package br.com.caju.persistence.shared.utils

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class DatabaseCleanUpService(private val jdbcTemplate: JdbcTemplate) {
    suspend operator fun invoke(excludeTables: List<String> = emptyList()) {
        jdbcTemplate.write(
            queries =
                mutableListOf<String>().apply {
                    add(START_TRANSACTION_QUERY)
                    add(DISABLE_FK_QUERY)
                    addAll(
                        getTablesNames()
                            .filterNot { excludeTables.contains(it.lowercase()) }
                            .map { DELETE_TABLE_QUERY.format(it) }
                    )
                    add(ENABLE_FK_QUERY)
                    add(COMMIT_TRANSACTION_QUERY)
                }
        )
    }

    private fun getTablesNames() =
        TABLE_NAMES.ifEmpty {
            jdbcTemplate.read(TABLES_NAMES_QUERY).also { TABLE_NAMES.addAll(it) }
        }

    companion object {
        private const val START_TRANSACTION_QUERY = "BEGIN;"
        private const val DISABLE_FK_QUERY = "SET session_replication_role = REPLICA;"
        private const val ENABLE_FK_QUERY = "SET session_replication_role = DEFAULT;"
        private const val DELETE_TABLE_QUERY = "DELETE FROM %s;"
        private const val COMMIT_TRANSACTION_QUERY = "COMMIT;"

        private const val TABLES_NAMES_QUERY =
            """
            SELECT
                TABLE_NAME
            FROM
                INFORMATION_SCHEMA.TABLES
            WHERE
                    TABLE_CATALOG = current_database()
                AND TABLE_SCHEMA = 'public'
                AND UPPER(TABLE_NAME) <> 'FLYWAY_SCHEMA_HISTORY';
        """

        private val TABLE_NAMES = mutableListOf<String>()
    }
}
