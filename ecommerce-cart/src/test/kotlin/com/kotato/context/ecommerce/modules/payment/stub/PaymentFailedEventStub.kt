package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.update.status.failed.PaymentFailedEvent
import java.time.ZonedDateTime

class PaymentFailedEventStub {
    companion object {
        fun random(aggregateId: String = PaymentIdStub.random().asString(),
                   createdOn: ZonedDateTime = ZonedDateTime.now())
                = PaymentFailedEvent(aggregateId, createdOn)
    }
}