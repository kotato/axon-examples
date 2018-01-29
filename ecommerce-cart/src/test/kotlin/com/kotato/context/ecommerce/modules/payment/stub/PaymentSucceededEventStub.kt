package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.update.status.succeeded.PaymentSucceededEvent
import java.time.ZonedDateTime

class PaymentSucceededEventStub {
    companion object {
        fun random(aggregateId: String = PaymentIdStub.random().asString(),
                   createdOn: ZonedDateTime = ZonedDateTime.now())
                = PaymentSucceededEvent(aggregateId, createdOn)
    }
}