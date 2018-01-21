package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import com.kotato.shared.stub.MoneyStub

class CartItemStub {
    companion object {
        fun random(itemId: ItemId = ItemIdStub.random(),
                   price: Money = MoneyStub.random()) = CartItem(itemId, price)
    }
}