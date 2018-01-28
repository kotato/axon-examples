package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import java.util.UUID

class OrderIdStub {
    companion object {
        fun random() = OrderId(UUID.randomUUID())
    }
}