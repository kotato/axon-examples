package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.CartItems

class CartItemsStub {
    companion object {
        fun random(): CartItems = (1..3).map { CartItemStub.random() to it }.toMap()
    }
}