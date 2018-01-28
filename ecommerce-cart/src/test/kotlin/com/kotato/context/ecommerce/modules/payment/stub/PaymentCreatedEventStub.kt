package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.create.PaymentCreatedEvent
import com.kotato.shared.stub.MoneyStub
import java.math.BigDecimal
import java.time.ZonedDateTime

class PaymentCreatedEventStub {
    companion object {
        fun random(aggregateId: String = PaymentIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   price: BigDecimal = MoneyStub.random().amount,
                   currency: String = MoneyStub.random().currency)
                = PaymentCreatedEvent(aggregateId, occurredOn, price, currency)
    }
}