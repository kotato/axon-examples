package com.kotato.context.ecommerce.modules.payment.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus

class PaymentStatusStub {
    companion object {
        fun random() = PaymentStatus.values().let { it[Faker().number().numberBetween(0, it.size)] }
    }
}