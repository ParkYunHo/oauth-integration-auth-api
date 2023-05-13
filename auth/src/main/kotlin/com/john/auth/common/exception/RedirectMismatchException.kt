package com.john.auth.common.exception

/**
 * @author yoonho
 * @since 2023.05.14
 */
class RedirectMismatchException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}