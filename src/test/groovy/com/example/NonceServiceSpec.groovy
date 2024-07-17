package com.example

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class NonceServiceSpec extends Specification implements ServiceUnitTest<NonceService>{

    def setup() {
    }

    void "a nonce should be generated when the instance is constructed"() {
        expect: "base64 encoded string from 32 random bytes"
        service.nonce != null
        service.nonce.length() == 44
    }

    void "each instance should generate a unique nonce"() {
        given: "another instance of NonceService"
        NonceService anotherNonceServiceInstance = new NonceService()

        expect: "the nonces to be different"
        service.nonce != anotherNonceServiceInstance.nonce
    }

    void "nonce should be immutable"() {
        given: "a generated nonce"
        String originalValue = service.nonce

        when: "something attempts to change the nonce value"
        // We're doing something wrong on purpose as part of the test, but we don't want IntelliJ to whine about it.
        // noinspection GrFinalVariableAccess,GroovyAccessibility
        service.nonce = 'new value'

        then: "an exception should be thrown"
        thrown(ReadOnlyPropertyException)

        and: "the value should remain unchanged"
        service.nonce == originalValue
    }
}
