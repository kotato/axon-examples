package com.kotato.context.ecommerce.modules.cart.adapter.checkout

import com.kotato.context.ecommerce.modules.cart.domain.checkout.CheckoutCartCommand
import com.kotato.cqrs.domain.command.CommandBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
open class CheckoutCartController(@Inject val commandBus: CommandBus) {

    @PostMapping("/ecommerce/cart/{cartId}/checkout")
    open fun checkout(@PathVariable("cartId") cartId: String): ResponseEntity<Unit> {
        commandBus.handle(CheckoutCartCommand(cartId))
        return ResponseEntity.noContent().build()
    }

}