package com.kotato.context.ecommerce.modules.cart.adapter.update

import com.kotato.context.ecommerce.modules.cart.domain.add.AddCartItemCommand
import com.kotato.context.ecommerce.modules.cart.domain.subtract.SubtractCartItemCommand
import com.kotato.cqrs.domain.command.CommandBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
open class CartItemController(private val commandBus: CommandBus) {

    @PatchMapping("/ecommerce/cart/{cartId}")
    open fun update(@PathVariable("cartId") cartId: String,
                    @RequestBody @Valid request: CartItemRestRequest): ResponseEntity<Unit> {
        if (request.quantity!! < 0) {
            commandBus.handle(SubtractCartItemCommand(cartId,
                                                      request.itemId!!.toString(),
                                                      Math.abs(request.quantity),
                                                      request.price!!,
                                                      request.currency!!))
        } else if (request.quantity > 0) {
            commandBus.handle(AddCartItemCommand(cartId,
                                                 request.itemId!!.toString(),
                                                 request.quantity,
                                                 request.price!!,
                                                 request.currency!!))
        }
        return ResponseEntity.noContent().build()
    }

}