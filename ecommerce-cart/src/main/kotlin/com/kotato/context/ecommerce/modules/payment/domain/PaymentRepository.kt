package com.kotato.context.ecommerce.modules.payment.domain

import com.kotato.shared.money.Money

interface PaymentRepository {

    fun new(orderId: PaymentId, amount: Money)
    fun search(paymentId: PaymentId): Payment?

}