package com.kotato.context.ecommerce.modules.cart.adapter.update

import com.kotato.context.ecommerce.modules.cart.domain.add.AddCartItemCommand
import com.kotato.context.ecommerce.modules.cart.domain.subtract.SubtractCartItemCommand
import com.kotato.cqrs.domain.command.CommandBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.validation.Valid

@RestController
open class CartItemController(@Inject val commandBus: CommandBus) {

    @PatchMapping("/ecommerce/cart/main")
    open fun potato(@RequestBody @Valid request: CartItemRestRequest): ResponseEntity<Unit> {
        if (request.quantity!! < 0) {
            commandBus.handle(SubtractCartItemCommand(request.cartId!!.toString(),
                                                      request.itemId!!.toString(),
                                                      Math.abs(request.quantity),
                                                      request.price!!,
                                                      request.currency!!))
        } else if (request.quantity > 0) {
            commandBus.handle(AddCartItemCommand(request.cartId!!.toString(),
                                                 request.itemId!!.toString(),
                                                 request.quantity,
                                                 request.price!!,
                                                 request.currency!!))
        }
        return ResponseEntity.noContent().build()
    }

}