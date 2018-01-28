package com.kotato.context.ecommerce.modules.payment.domain.create

import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.order.domain.create.OrderCreatedEvent
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.shared.money.Money
import org.axonframework.eventhandling.EventHandler
import java.math.BigDecimal
import javax.inject.Named

@Named
open class CreatePaymentOnOrderCreatedEventHandler(private val creator: PaymentCreator) {

    @EventHandler
    open fun on(event: OrderCreatedEvent) {
        creator(PaymentId.fromString(event.paymentId), event.price())
    }

    private fun OrderCreatedEvent.price() =
            this.cartItems.toCartItems()
                    .map { it.key.price.toEur() * it.value }
                    .reduce(Money::plus)

    //This should be a currency converter
    private fun Money.toEur() =
            this.copy(amount = this.amount.convert(this.currency), currency = "EUR")

    private fun BigDecimal.convert(currency: String) = this * BigDecimal.ONE
}