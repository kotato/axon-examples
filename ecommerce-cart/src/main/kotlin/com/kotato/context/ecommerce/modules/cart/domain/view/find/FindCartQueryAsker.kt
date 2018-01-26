package com.kotato.context.ecommerce.modules.cart.domain.view.find

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import javax.inject.Named

@Named
open class FindCartQueryAsker(private val queryBus: QueryBus) {

    fun ask(cartId: CartId) = queryBus.ask<CartResponse>(FindCartQuery(cartId.asString()))

}