package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.19
 */
class NotRegisteredUserException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}