package com.kotato.context.ecommerce.modules.cart.stub

import com.github.javafaker.Faker
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemSubtractedEvent
import com.kotato.shared.stub.BigDecimalStub
import java.math.BigDecimal
import java.time.ZonedDateTime

class CartItemSubtractedEventStub {
    companion object {
        fun random(aggregateId: String = CartIdStub.random().asString(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   itemId: String = ItemIdStub.random().asString(),
                   quantity: Int = Faker().number().numberBetween(0, 100),
                   price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR") =
                CartItemSubtractedEvent(aggregateId, occurredOn, itemId, quantity, price, currency)
    }
}