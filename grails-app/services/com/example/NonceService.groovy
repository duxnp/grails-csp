package com.example

import groovy.transform.CompileStatic
import java.security.SecureRandom
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode

/**
 * <p>
 *     Generates an immutable base64 encoded number intended to only be used once (per scope).
 *     The scope dictates the lifetime of the nonce.
 * </p>
 * <p>
 *     Normally you can control the scope by adding a property like this to the service:
 *     <pre>static scope = "request"</pre>
 * </p>
 * <p>
 *     However, this exception may be thrown in some situations:
 *     ScopeNotActiveException: Error creating bean with name 'nonceService': Scope 'request' is not active for the
 *     current thread; consider defining a scoped proxy for this bean...
 *     This happens when Spring tries to create an instance of the service too early.
 * </p>
 * <p>
 *     The solution is to use ScopedProxyFactoryBean. The proxy will prevent the exception by injecting the actual
 *     service only when it's safe to do so.
 *     For example, add the following to resources.groovy to configure this service to be scoped per request:
 * </p>
 * <pre>
 * import org.springframework.aop.scope.ScopedProxyFactoryBean
 * beans = {
 *     perRequestNonceServiceImpl(NonceService) { bean -> bean.scope = "request" }
 *     perRequestNonceService(ScopedProxyFactoryBean) {
 *         targetBeanName = 'perRequestNonceServiceImpl'
 *         proxyTargetClass = true
 *     }
 * }
 * </pre>
 *
 */
@CompileStatic
class NonceService {

    final String nonce

    NonceService() {
        byte[] randomBytes = new byte[32]
        new SecureRandom().nextBytes(randomBytes)
        this.nonce = Base64.encoder.encodeToString(randomBytes)
    }

}
