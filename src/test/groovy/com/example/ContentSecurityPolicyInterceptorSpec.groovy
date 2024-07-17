package com.example

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class ContentSecurityPolicyInterceptorSpec extends Specification implements InterceptorUnitTest<ContentSecurityPolicyInterceptor> {

    NonceService nonceService

    def setup() {
        // GroovyMock must be used instead of Mock because NonceService is using at least one Groovy specific feature.
        // Groovy is automatically generating the getter method for the nonce property.
        nonceService = GroovyMock(NonceService)
        interceptor.perRequestNonceService = nonceService
    }

    void "Test contentSecurityPolicy interceptor matching"() {
        when: "the app handles a request"
        withRequest(uri:"/")

        then: "the interceptor does match"
        interceptor.doesMatch()
    }

    void "The CSP header should be added to the response"() {
        when: "a controller action returns"
        // The after method is normally called automatically as part of the interceptor's lifecycle when the matched
        // controller action returns
        interceptor.after()

        then: "the interceptor generates the CSP with at least one nonce"
        (1.._) * nonceService.getNonce()

        and: "adds the CSP header to the response"
        interceptor.response.getHeader('Content-Security-Policy') != null
    }
}
