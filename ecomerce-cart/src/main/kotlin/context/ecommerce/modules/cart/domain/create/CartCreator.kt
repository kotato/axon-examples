package context.ecommerce.modules.cart.domain.create

import context.ecommerce.modules.cart.domain.CartId
import javax.inject.Named

@Named
open class CartCreator(private val repository: CartRepository) {

    operator fun invoke(id: CartId) {
        repository.new(id)
    }

}