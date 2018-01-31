package com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id

import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewByPaymentIdNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class OrderByPaymentIdFinder(private val repository: OrderViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(id: PaymentId)
            = repository.searchByPaymentId(id).also { guardOrderExists(id, it) }!!

    private fun guardOrderExists(id: PaymentId, order: OrderView?) {
        order ?: throw OrderViewByPaymentIdNotFoundException(id.asString())
    }

}