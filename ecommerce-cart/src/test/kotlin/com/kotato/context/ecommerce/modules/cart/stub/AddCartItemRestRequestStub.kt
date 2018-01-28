package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.shared.stub.BigDecimalStub
import java.math.BigDecimal
import java.util.UUID

class AddCartItemRestRequestStub {
    companion object {
        fun random(itemId: UUID = ItemIdStub.random().id,
                   price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR",
                   quantity: Int = Faker().number().numberBetween(1, 100)
                  ) = CartItemRestRequest(itemId, price, currency, quantity)
    }
}