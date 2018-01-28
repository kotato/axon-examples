package com.kotato.context.ecommerce.modules.payment.domain.create

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentRepository
import com.kotato.shared.money.Money
import javax.inject.Named

@Named
open class PaymentCreator(private val repository: PaymentRepository) {

    operator fun invoke(paymentId: PaymentId, amount: Money) {
        repository.new(paymentId, amount)
    }

}