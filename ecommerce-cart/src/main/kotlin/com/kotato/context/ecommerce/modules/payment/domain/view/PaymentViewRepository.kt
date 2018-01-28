package com.kotato.context.ecommerce.modules.payment.domain.view

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId

interface PaymentViewRepository {

    fun save(view: PaymentView)
    fun search(id: PaymentId): PaymentView?

}