package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import java.time.ZonedDateTime

class CartViewStub {
    companion object {
        fun random(id: CartId = CartIdStub.random(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   userId: UserId = UserIdStub.random(),
                   cartItems: CartItems = CartItemsStub.random(),
                   checkout: Boolean = false) =
                CartView(id, occurredOn, userId, cartItems, checkout)
    }
}