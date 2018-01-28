package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import java.time.ZonedDateTime

class CartCreatedEventStub {
    companion object {
        fun random(aggregateId: String = CartIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   userId: String = UserIdStub.random().asString()) =
                CartCreatedEvent(aggregateId, occurredOn, userId)
    }
}