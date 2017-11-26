package context.ecommerce.modules.cart.domain.create

import context.ecommerce.modules.cart.domain.Cart
import context.ecommerce.modules.cart.domain.CartId
import context.ecommerce.modules.user.domain.UserId

interface CartRepository {

    fun new(id: CartId, userId: UserId)
    fun search(id: CartId): Cart?

}