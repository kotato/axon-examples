package context.ecommerce.modules.cart.domain.create

import context.ecommerce.modules.cart.domain.Cart
import context.ecommerce.modules.cart.domain.CartId

interface CartRepository {

    fun new(id: CartId)
    fun search(id: CartId): Cart?

}