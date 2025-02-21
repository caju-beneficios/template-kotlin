package br.com.caju.shared.utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.delete
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON
import com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE
import com.github.tomakehurst.wiremock.matching.UrlPattern
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.HttpStatus

private fun HttpMethod.toMatcherMapping(matcher: UrlPattern) =
    when (this) {
        GET -> get(matcher)
        POST -> post(matcher)
        PUT -> put(matcher)
        DELETE -> delete(matcher)
        else -> error("HttpMethod not implemented")
    }

fun HttpMethod.toMapping(url: String): MappingBuilder = toMatcherMapping(urlEqualTo(url))

fun HttpMethod.toMapping(urlPattern: Regex): MappingBuilder =
    toMatcherMapping(urlPathMatching(urlPattern.pattern))

fun MappingBuilder.responseAs(
    httpStatus: HttpStatus,
    responseBody: String,
    contentType: String = APPLICATION_JSON,
    block: ResponseDefinitionBuilder.() -> Unit = {},
): MappingBuilder =
    willReturn(
        aResponse()
            .withStatus(httpStatus.value())
            .withHeader(CONTENT_TYPE, contentType)
            .withBody(responseBody)
            .apply { block() }
    )

fun MappingBuilder.responseAs(
    httpStatus: HttpStatus,
    responseBody: ByteArray,
    contentType: String,
    block: ResponseDefinitionBuilder.() -> Unit = {},
): MappingBuilder =
    willReturn(
        aResponse()
            .withStatus(httpStatus.value())
            .withHeader(CONTENT_TYPE, contentType)
            .withBody(responseBody)
            .apply { block() }
    )

fun WireMockServer.withStub(
    method: HttpMethod,
    url: String,
    httpStatus: HttpStatus,
    responseBody: String,
    headers: Map<String, String> = emptyMap(),
) {
    stubFor(
        method.toMapping(url).responseAs(httpStatus, responseBody) {
            headers.entries.forEach { (k, v) -> withHeader(k, v) }
        }
    )
}

fun WireMockServer.withStub(
    method: HttpMethod,
    url: String,
    httpStatus: HttpStatus,
    responseBody: String,
    headers: Map<String, String> = emptyMap(),
    block: MappingBuilder.() -> MappingBuilder,
) {
    stubFor(
        method.toMapping(url).block().responseAs(httpStatus, responseBody) {
            headers.entries.forEach { (k, v) -> withHeader(k, v) }
        }
    )
}

fun WireMockServer.withStub(
    method: HttpMethod,
    url: String,
    httpStatus: HttpStatus,
    responseBody: ByteArray,
    contentType: String,
) {
    stubFor(method.toMapping(url).responseAs(httpStatus, responseBody, contentType))
}

fun WireMockServer.withStub(
    method: HttpMethod,
    urlPattern: Regex,
    httpStatus: HttpStatus,
    responseBody: String,
) {
    stubFor(method.toMapping(urlPattern).responseAs(httpStatus, responseBody))
}

fun WireMockServer.withStub(
    method: HttpMethod,
    urlPattern: Regex,
    httpStatus: HttpStatus,
    responseBody: ByteArray,
    contentType: String,
) {
    stubFor(method.toMapping(urlPattern).responseAs(httpStatus, responseBody, contentType))
}
