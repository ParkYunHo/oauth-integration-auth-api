package com.john.auth.authorization.adapter.out

import com.john.auth.authorization.application.port.out.RestCallPort
import com.john.auth.common.constants.RestCallClientType
import com.john.auth.common.exception.BadRequestException
import com.john.auth.common.utils.EnvironmentUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * @author yoonho
 * @since 2023.05.28
 */
@Repository
class MemberHttpClientAdapter(
    private val memberHttpClient: HttpClient
): RestCallPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * ResourceServer 로그아웃API 호출 (HttpClient)
     *
     * @param userId [String]
     * @author yoonho
     * @since 2023.05.28
     */
    override fun restCallLogout(userId: String) {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("userId", userId)

        val response = memberHttpClient.send(
            this.httpRequestBuilder(path = "/api/logout", queryParams = queryParams)
                .POST(HttpRequest.BodyPublishers.ofString(this.formDataAsString(formData = queryParams)))
                .header("Content-Type", "application/json;charset=UTF-8")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )

        if(response.statusCode() in 200 until 300) {
            log.info(" >>> [restCallLogout] response: ${response.body()}")
        }else{
            log.error(" >>> [restCallLogout] Fail to call the Logout API - status: ${response.statusCode()}, body: ${response.body()}")
            throw BadRequestException("Fail to call the Logout API")
        }
    }

    /**
     * RestCall 통신Client 지원여부 조회
     *
     * @param type [String]
     * @return [Boolean]
     * @author yoonho
     * @since 2023.05.28
     */
    override fun support(type: String): Boolean =
        type == RestCallClientType.HTTPCLIENT.code

    /**
     * HttpRequest Builder 리턴
     *
     * @param path [String]
     * @param queryParams [MultiValueMap]<[String], [String]>
     * @return [HttpRequest.Builder]
     * @author yoonho
     * @since 2023.05.28
     */
    private fun httpRequestBuilder(path: String, queryParams: MultiValueMap<String, String>): HttpRequest.Builder {
        val schema = EnvironmentUtils.getProperty("auth.conn.http-client.schema", "http")
        val host = EnvironmentUtils.getProperty("auth.conn.http-client.host", "localhost:8080")
        val readTimeout = EnvironmentUtils.getProperty("auth.conn.http-client.read-timeout", "5000").toLong()

        val urlPath = "$schema://$host$path"
        return HttpRequest.newBuilder()
            .uri(
                UriComponentsBuilder
                    .fromUriString(urlPath)
                    .queryParams(queryParams)
                    .build().toUri()
            )
            .timeout(Duration.ofMillis(readTimeout))
    }

    /**
     * FormData > String 변환
     *
     * @param formData [Map]<[String], [Any]>
     * @return [String]
     * @author yoonho
     * @since 2023.05.28
     */
    private fun formDataAsString(formData: Map<String, Any>): String {
        val sb = StringBuilder()
        for(entry: Map.Entry<String, Any> in formData.entries) {
            if(sb.isNotEmpty()) {
                sb.append("&")
            }
            sb.append(entry.key).append("=").append(entry.value)
        }
        return sb.toString()
    }
}