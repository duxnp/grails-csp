package com.example

import grails.util.Environment

class ContentSecurityPolicyInterceptor {

    // perRequestNonceService is also injected into NonceTagLib, so the nonce rendered by NonceTagLib will match the one
    // added to the 'Content-Security-Policy' header.
    NonceService perRequestNonceService

    ContentSecurityPolicyInterceptor() {
        // Console is only available when environment == development
        matchAll()
            .excludes(controller: 'console')
    }

    boolean before() { true }

    boolean after() {

        List<String> cspValues = []
        cspValues.add("default-src 'self' http: 'nonce-${perRequestNonceService.nonce}';")
        cspValues.add("script-src 'self' 'nonce-${perRequestNonceService.nonce}';")
        cspValues.add("script-src-elem 'self' 'nonce-${perRequestNonceService.nonce}';")
        cspValues.add("object-src 'none';")
        cspValues.add("form-action 'self';")
        cspValues.add("frame-ancestors 'none';")
        cspValues.add("connect-src 'self' www.google-analytics.com;")
        cspValues.add("style-src 'self';")
        cspValues.add("style-src-elem 'self' 'nonce-${perRequestNonceService.nonce}';")
        cspValues.add("font-src 'self' fonts.gstatic.com;")
        cspValues.add("img-src 'self' data: www.google-analytics.com;")

        if (Environment.current != Environment.DEVELOPMENT) {
            cspValues.add("upgrade-insecure-requests;")
        }

        response.setHeader(
            'Content-Security-Policy',
            cspValues.join(" ")
        )
        true
    }

    void afterView() {
        // no-op
    }

}