package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.14
 */
class InvalidScopeException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}