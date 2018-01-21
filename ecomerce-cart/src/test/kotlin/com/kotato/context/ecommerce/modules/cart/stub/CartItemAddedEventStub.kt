package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.shared.stub.BigDecimalStub
import java.math.BigDecimal
import java.time.ZonedDateTime

class CartItemAddedEventStub {
    companion object {
        fun random(aggregateId: String = CartIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   itemId: String = ItemIdStub.random().asString(),
                   quantity: Int = Faker().number().numberBetween(0, 100),
                   price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR") =
                CartItemAddedEvent(aggregateId, occurredOn, itemId, quantity, price, currency)
    }
}