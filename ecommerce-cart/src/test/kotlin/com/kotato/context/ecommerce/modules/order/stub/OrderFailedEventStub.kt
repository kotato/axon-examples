package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.update.status.failed.OrderFailedEvent
import java.time.ZonedDateTime

class OrderFailedEventStub {
    companion object {
        fun random(aggregateId: String = OrderIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now())
                = OrderFailedEvent(aggregateId, occurredOn)
    }
}