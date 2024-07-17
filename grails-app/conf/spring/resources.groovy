import org.springframework.aop.scope.ScopedProxyFactoryBean
import com.example.NonceService

// Place your Spring DSL code here to configure beans.
// Beans are objects that are instantiated, assembled, and managed by a Spring IoC container.
// In other words, dependency injection for Spring.
beans = {
    // Implementation of the service
    perRequestNonceServiceImpl(NonceService) { bean -> bean.scope = "request" }
    // Proxy for the service. When the app asks for 'perRequestNonceService', an instance of ScopedProxyFactoryBean will be injected.
    // An instance of NonceService will be injected when the proxy asks for the 'perRequestNonceServiceImpl' bean.
    perRequestNonceService(ScopedProxyFactoryBean) {
        // Instantiate the ScopedProxyFactoryBean class with these properties
        targetBeanName = 'perRequestNonceServiceImpl'
        proxyTargetClass = true
    }
}
