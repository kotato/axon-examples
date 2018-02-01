package com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id

import com.kotato.context.ecommerce.modules.order.domain.view.toResponse
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.cqrs.domain.query.QueryHandler
import javax.inject.Named

@Named
open class FindOrderByPaymentIdQueryHandler(private val finder: OrderByPaymentIdFinder) {

    @QueryHandler
    open fun on(query: FindOrderByPaymentIdQuery)
            = finder(PaymentId.fromString(query.id)).toResponse()

}