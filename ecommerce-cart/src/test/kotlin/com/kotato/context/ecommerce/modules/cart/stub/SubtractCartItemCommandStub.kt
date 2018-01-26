package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.domain.subtract.SubtractCartItemCommand
import com.kotato.shared.stub.BigDecimalStub
import java.math.BigDecimal

class SubtractCartItemCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString(),
                   itemId: String = ItemIdStub.random().asString(),
                   quantity: Int = Faker().number().numberBetween(0, 10),
                   price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR") =
                SubtractCartItemCommand(id, itemId, quantity, price, currency)
    }
}