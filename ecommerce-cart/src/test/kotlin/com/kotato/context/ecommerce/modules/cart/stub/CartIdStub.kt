package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import java.util.UUID

class CartIdStub {
    companion object {
        fun random() = CartId(UUID.randomUUID())
    }
}