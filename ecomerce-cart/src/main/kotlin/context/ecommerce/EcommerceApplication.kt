package context.ecommerce

import cqrs.application.EnableCqrs
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableCqrs
open class EcommerceApplication {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(EcommerceApplication::class.java, *args)
        }
    }
}
