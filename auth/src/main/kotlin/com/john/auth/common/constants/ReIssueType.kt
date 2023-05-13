package com.john.auth.common.constants

/**
 * @author yoonho
 * @since 2023.05.13
 */
enum class ReIssueType(
    val code: String,
    val desc: String
) {
    NATIVE("native", "NATIVE(IOS, Android)"),
    REST("rest", "REST API"),
    JAVASCRIPT("js", "javascript"),
    ADMIN("admin", "ADMIN"),
    SECRET("secret", "Client Secret");

    companion object {

        /**
         * 유효한 업데이트 항목(target) 여부 체크
         * <p>
         *     - true: 유효하지 않는 업데이트 항목(target)일 경우
         *     - false: 유효한 업데이트 항목(target)일 경우
         *
         * @param target [String]
         * @return [Boolean]
         * @author yoonho
         * @since 2023.05.13
         */
        fun invalidTarget(target: String): Boolean {
            values()
                .filter { it.code == target }
                .map { return false }

            return true
        }
    }
}