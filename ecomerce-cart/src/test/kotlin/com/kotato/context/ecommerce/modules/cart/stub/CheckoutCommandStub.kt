package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.checkout.CheckoutCommand

class CheckoutCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString()) = CheckoutCommand(id)
    }
}