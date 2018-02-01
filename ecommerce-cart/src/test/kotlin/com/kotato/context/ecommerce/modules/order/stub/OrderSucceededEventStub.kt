package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.order.domain.update.status.succeeded.OrderSucceededEvent
import java.time.ZonedDateTime

class OrderSucceededEventStub {
    companion object {
        fun random(aggregateId: String = OrderIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now())
                = OrderSucceededEvent(aggregateId, occurredOn)
    }
}