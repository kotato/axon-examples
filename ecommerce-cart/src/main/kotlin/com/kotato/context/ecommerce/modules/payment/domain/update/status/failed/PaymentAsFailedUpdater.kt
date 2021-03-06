package com.kotato.context.ecommerce.modules.payment.domain.update.status.failed

import com.kotato.context.ecommerce.modules.payment.domain.PaymentNotFound
import com.kotato.context.ecommerce.modules.payment.domain.Payment
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentRepository
import javax.inject.Named

@Named
open class PaymentAsFailedUpdater(private val repository: PaymentRepository) {

    operator fun invoke(id: PaymentId) {
        repository.search(id)
                .also { guardPaymentExists(id, it) }!!
                .updateAsFailed()
    }

    private fun guardPaymentExists(id: PaymentId, payment: Payment?) {
        payment ?: throw PaymentNotFound(id.asString())
    }

}