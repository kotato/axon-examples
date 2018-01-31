package com.kotato.context.ecommerce.modules.order.domain.update.status.failed

import com.kotato.context.ecommerce.modules.order.domain.Order
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.OrderRepository
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.FindOrderByPaymentIdQueryAsker
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.cqrs.domain.query.QueryBus
import javax.inject.Named

@Named
open class OrderStatusAsFailedByPaymentIdUpdater(private val repository: OrderRepository,
                                                 private val asker: FindOrderByPaymentIdQueryAsker) {

    operator fun invoke(paymentId: PaymentId) {
        val orderId = asker.ask(paymentId)
                .let { OrderId.fromString(it.id) }

        orderId.let(repository::search)
                .also { guardOrderExists(orderId, it) }!!
                .updateAsFailed()
    }

    private fun guardOrderExists(id: OrderId, order: Order?) {
        order ?: throw OrderNotFoundException(id.asString())
    }

}