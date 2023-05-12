package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.12
 */
class BadRequestException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}