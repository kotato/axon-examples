package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.checkout.CheckoutCartCommand

class CheckoutCartCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString()) = CheckoutCartCommand(id)
    }
}