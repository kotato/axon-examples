package com.kotato.context.ecommerce.modules.payment.domain

import com.kotato.shared.money.Money
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Payment {

    @AggregateIdentifier
    lateinit var paymentId: PaymentId
        private set
    lateinit var amount: Money
        private set
    lateinit var status: PaymentStatus
        private set

    companion object {
        fun create(paymentId: PaymentId, amount: Money) {

        }
    }
}