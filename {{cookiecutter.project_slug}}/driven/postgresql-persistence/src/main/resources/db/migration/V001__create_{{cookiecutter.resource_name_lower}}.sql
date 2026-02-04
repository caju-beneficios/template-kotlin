CREATE TABLE {{cookiecutter.resource_name_plural.lower()}}
(
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    version     INTEGER      NOT NULL,{% if cookiecutter.resource_name_lower == 'article' %}
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    status      VARCHAR(50)  NOT NULL,{% else %}    -- TODO: Add entity columns here
{% endif %}
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP{% if cookiecutter.resource_name_lower == 'article' %},
    CONSTRAINT uc_{{cookiecutter.resource_name_lower}}_title UNIQUE (title)
{% else %}    -- TODO: Add unique constraints here
{% endif %});