package com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id

import com.kotato.cqrs.domain.query.Query


data class FindOrderByPaymentIdQuery(val id: String) : Query