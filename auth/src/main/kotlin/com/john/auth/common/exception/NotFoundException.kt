package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.13
 */
class NotFoundException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}