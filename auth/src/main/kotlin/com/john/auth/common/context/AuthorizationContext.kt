package com.john.auth.common.context

class AuthorizationContext {

    companion object {

        private val authorizationToken = ThreadLocal<AuthorizationToken?>()

        var AUTHORIZATIONTOKEN: AuthorizationToken?
            get() = authorizationToken.get()
            set(authorizationToken: AuthorizationToken?) {
                Companion.authorizationToken.set(authorizationToken)
            }

        fun remove() {
            authorizationToken.remove()
        }
    }

}