package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.30
 */
class InternalServerException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}