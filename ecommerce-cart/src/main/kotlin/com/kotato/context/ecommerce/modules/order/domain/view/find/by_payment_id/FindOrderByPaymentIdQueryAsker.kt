package com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id

import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import javax.inject.Named

@Named
open class FindOrderByPaymentIdQueryAsker(private val queryBus: QueryBus) {

    fun ask(id: PaymentId)
            = queryBus.ask<OrderResponse>(FindOrderByPaymentIdQuery(id.asString()))

}