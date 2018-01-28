package com.kotato.context.ecommerce.modules.cart.domain.view.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.eventhandling.EventHandler
import javax.inject.Named

@Named
open class CreateCartViewOnCartCreatedEventHandler(private val creator: CartViewCreator) {

    @EventHandler
    fun on(event: CartCreatedEvent) {
        creator(CartId.fromString(event.aggregateId()),
                event.occurredOn(),
                UserId.fromString(event.userId))
    }

}