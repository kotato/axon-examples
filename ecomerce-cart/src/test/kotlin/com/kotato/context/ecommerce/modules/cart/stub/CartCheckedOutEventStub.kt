package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartCheckedOutEvent
import com.kotato.context.ecommerce.modules.order.stub.OrderIdStub
import java.time.ZonedDateTime

class CartCheckedOutEventStub {
    companion object {
        fun random(aggregateId: String = CartIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   orderId: String = OrderIdStub.random().asString()) =
                CartCheckedOutEvent(aggregateId, occurredOn, orderId)
    }
}