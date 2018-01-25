package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.domain.SerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import java.time.ZonedDateTime

class CartResponseStub {
    companion object {
        fun random(cartId: String = CartIdStub.random().asString(),
                   createdOn: ZonedDateTime = ZonedDateTime.now(),
                   cartItems: SerializedCartItems = CartItemsStub.random().toSerializedCartItems(),
                   checkout: Boolean = Faker().bool().bool())
                = CartResponse(cartId, createdOn, cartItems, checkout)
    }
}