package com.example

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class NonceTagLibSpec extends Specification implements TagLibUnitTest<NonceTagLib> {

    NonceService nonceService

    def setup() {
        // GroovyMock must be used instead of Mock because NonceService is using at least one Groovy specific feature.
        // Groovy is automatically generating the getter method for the nonce property.
        nonceService = GroovyMock(NonceService)
        tagLib.perRequestNonceService = nonceService
    }

    void "should render the nonce from NonceService"() {
        when: "the custom tag is used"
        def output = tagLib.perRequestNonce().toString()

        then: "it gets the nonce from the service"
        // With mocked objects you can mock the return value of a method, but the syntax is a bit confusing at first.
        // The assertion below reads like this:
        //   "When tagLib.perRequestNonce() is called, then we expect nonceService.getNonce() to have been called once."
        // The weird part is that this is also where you define the mocked return value of the collaborator... AFTER
        // calling the method that would trigger the collaborator's method to be called.
        1 * nonceService.getNonce() >> 'super-secure-nonce'

        and: "renders it"
        output == 'super-secure-nonce'
    }
}
