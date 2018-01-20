package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.shared.stub.BigDecimalStub
import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

class SubtractCartItemRestRequestStub {
    companion object {
        fun random(cartId: UUID = CartIdStub.random().id,
                   itemId: UUID = ItemIdStub.random().id,
                   price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR",
                   quantity: Int = Faker().number().numberBetween(-100, -1)
                   ) = CartItemRestRequest(cartId, itemId, price, currency, quantity)
    }
}