package com.kotato.context.ecommerce.modules.cart.adapter.find

import com.kotato.context.ecommerce.modules.cart.domain.view.CartResponse
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQuery
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
open class FindCartController(private val queryBus: QueryBus) {

    @GetMapping("/ecommerce/cart/{cartId}")
    open fun find(@PathVariable("cartId") id: String) =
            queryBus.ask<CartResponse>(FindCartQuery(id))
                    .toRestResponse()
                    .let { ResponseEntity.ok(it) }

}