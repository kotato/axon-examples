package com.kotato.context.ecommerce.modules.order.behaviour.view

import com.kotato.context.ecommerce.modules.order.behaviour.OrderId

interface OrderViewRepository {

    fun search(id: OrderId): OrderView?
    fun save(view: OrderView)

}