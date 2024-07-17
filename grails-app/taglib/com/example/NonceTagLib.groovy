package com.example


class NonceTagLib {

    // perRequestNonceService matches the bean name defined in resources.groovy
    NonceService perRequestNonceService

    static defaultEncodeAs = [taglib:'html']

    /**
     * Renders the nonce from the request scoped NonceService.
     * Can be used as a tag or as a function:
     *  <pre>
     *      <g:perRequestNonce/>
     *      ${perRequestNonce()}
     *  </pre>
     */
    def perRequestNonce = { attrs, body ->
        out << body() << (perRequestNonceService.nonce)
    }
}