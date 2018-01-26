package com.kotato.context.ecommerce.modules.cart.domain.view.find

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.toResponse
import com.kotato.cqrs.domain.query.QueryHandler
import javax.inject.Named

@Named
open class FindCartQueryHandler(private val finder: CartFinder) {

    @QueryHandler
    open fun on(query: FindCartQuery) = finder(CartId.fromString(query.id)).toResponse()

}