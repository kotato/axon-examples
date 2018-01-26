package com.kotato.context.ecommerce.modules.payment.stub

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import java.util.UUID

class PaymentIdStub {
    companion object {
        fun random() = PaymentId(UUID.randomUUID())
    }
}