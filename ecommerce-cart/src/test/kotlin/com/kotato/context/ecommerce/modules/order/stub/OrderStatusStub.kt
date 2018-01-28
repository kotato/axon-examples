package com.kotato.context.ecommerce.modules.order.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus

class OrderStatusStub {
    companion object {
        fun random() = OrderStatus.values().let { it[Faker().number().numberBetween(0, it.size)] }
    }
}