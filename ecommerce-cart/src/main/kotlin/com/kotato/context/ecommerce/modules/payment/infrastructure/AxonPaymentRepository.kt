package com.kotato.context.ecommerce.modules.payment.infrastructure

import com.kotato.context.ecommerce.modules.payment.domain.Payment
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentRepository
import com.kotato.shared.money.Money
import org.axonframework.commandhandling.model.AggregateNotFoundException
import org.springframework.stereotype.Repository
import org.axonframework.commandhandling.model.Repository as AggregateRepository

@Repository
open class AxonPaymentRepository(private val persistenceRepository: AggregateRepository<Payment>) : PaymentRepository {

    override fun search(paymentId: PaymentId) =
            try {
                persistenceRepository.load(paymentId.asString()).invoke { it }
            } catch (exception: AggregateNotFoundException) {
                null
            }

    override fun new(orderId: PaymentId, amount: Money) {
        persistenceRepository.newInstance {
            Payment.create(orderId, amount)
            Payment()
        }
    }

}