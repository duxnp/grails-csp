# Grails Content Security Policy

---

This is an example of how to implement Content Security Policy in a Grails application.

Grails is built on top of Spring and usually handles all the Spring stuff for you. In this case, some additional manual bean configuration had to be done using grails-app/conf/spring/resources.groovy.

## NonceService

This service class generates a read only nonce when it's instantiated. We need a different nonce value for each request though. resources.groovy registers an additional bean called perRequestNonceService which is scoped per request. Actually two additional beans, but you can read about that in the comments in resources.groovy.

## NonceTagLib

This custom tag simply renders the nonce value from NonceService. Instead of just asking for a bean named "nonceService", it's specifically asking for the one named "perRequestNonceService". So Spring will automatically inject the instance of NonceService that was created for that particular request.

## ContentSecurityPolicyInterceptor

This interceptor adds the Content-Security-Policy header to every response. It's also asking for a bean named "perRequestNonceService". It uses the nonce value from that service wherever a nonce is needed in the header value. This ensures that the nonce added to GSP templates and the CSP header match. 