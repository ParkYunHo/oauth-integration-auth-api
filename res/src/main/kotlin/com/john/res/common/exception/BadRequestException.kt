package com.john.res.common.exception

/**
 * @author yoonho
 * @since 2023.05.10
 */
class BadRequestException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}