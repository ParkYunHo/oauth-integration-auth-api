package com.john.auth.authorization.adapter.`in`.web

import com.john.auth.AuthApplicationTests
import io.kotest.core.spec.style.BehaviorSpec
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.util.LinkedMultiValueMap

/**
 * @author yoonho
 * @since 2023.05.28
 */
@SpringBootTest(classes = [AuthApplicationTests::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class AuthorizationControllerTest(
    private val mockMvc: MockMvc,
): BehaviorSpec({
    val log = LoggerFactory.getLogger(this::class.java)

    Given("먼저, 로그아웃API 요청이 세팅되어 있다.") {
        val accessToken = "TEST_ACCESS_TOKEN"
        val userId = "TEST_USER_ID"

        val paramMap = LinkedMultiValueMap<String, String>()
        paramMap.add("target_id_type", "user_id")
        paramMap.add("target_id", userId)

        When("만약, 로그아웃API를 호출하면") {
            val result = mockMvc.get("/oauth/logout?user") {
                header("Authorization", "Bearer $accessToken")
                contentType = MediaType.APPLICATION_JSON
                params = paramMap
            }

            Then("그러면, 로그아웃API 호출결과를 확인한다.") {
                try {
                    val mvcResult = result.andDo { print() }.andReturn()
                }catch (e: Exception) {
                    log.error(" >>> [logout] Exception occurs - message: ${e.message}")
                }
            }
        }
    }
})