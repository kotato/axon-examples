package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.checkout.CheckoutCartCommand
import com.kotato.context.ecommerce.modules.order.stub.OrderIdStub

class CheckoutCartCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString(),
                   orderId: String = OrderIdStub.random().asString()) = CheckoutCartCommand(id, orderId)
    }
}