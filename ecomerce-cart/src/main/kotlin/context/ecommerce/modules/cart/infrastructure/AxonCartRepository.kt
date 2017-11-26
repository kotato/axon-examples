package context.ecommerce.modules.cart.infrastructure

import context.ecommerce.modules.cart.domain.Cart
import context.ecommerce.modules.cart.domain.CartId
import context.ecommerce.modules.cart.domain.create.CartRepository
import context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateNotFoundException
import org.springframework.stereotype.Repository
import org.axonframework.commandhandling.model.Repository as AggregateRepository

@Repository
open class AxonCartRepository(private val persistenceRepository: AggregateRepository<Cart>) : CartRepository {
    override fun new(id: CartId, userId: UserId) {
        persistenceRepository.newInstance {
            Cart.create(id, userId)
            Cart()
        }
    }

    override fun search(id: CartId): Cart? =
            try {
                persistenceRepository.load(id.asString()).invoke { it }
            } catch (exception: AggregateNotFoundException) {
                null
            }
}