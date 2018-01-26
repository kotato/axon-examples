package com.kotato.context.ecommerce.modules.order.behaviour.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.order.behaviour.OrderId

interface OrderViewRepository {

    fun save(view: OrderView)
    fun search(id: OrderId): OrderView?
    fun searchByCartId(id: CartId): OrderView?

}