package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.Amount

class CartItemsStub {
    companion object {
        fun random() = (1..3).map { CartItemStub.random() to Amount(it) }.toMap()
    }
}