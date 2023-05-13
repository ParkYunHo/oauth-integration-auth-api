package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.14
 */
class UnsupportedResponseTypeException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}