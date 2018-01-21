package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.item.domain.ItemId
import java.util.UUID

class ItemIdStub {
    companion object {
        fun random() = ItemId(UUID.randomUUID())
    }
}