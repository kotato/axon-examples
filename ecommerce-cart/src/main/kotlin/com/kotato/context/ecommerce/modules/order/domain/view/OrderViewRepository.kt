package com.kotato.context.ecommerce.modules.order.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId

interface OrderViewRepository {

    fun save(view: OrderView)
    fun search(id: OrderId): OrderView?
    fun searchByCartId(id: CartId): OrderView?
    fun searchByPaymentId(id: PaymentId): OrderView?

}