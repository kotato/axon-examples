package com.kotato.context.ecommerce.modules.cart.adapter.create

import com.kotato.context.ecommerce.modules.cart.domain.create.CreateCartCommand
import com.kotato.cqrs.domain.command.CommandBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
open class CreateCartController(private val commandBus: CommandBus) {

    @PostMapping("/ecommerce/cart")
    open fun create(@RequestBody @Valid request: CreateCartRestRequest): ResponseEntity<Unit> {
        commandBus.handle(CreateCartCommand(request.id!!.toString(),
                                            request.userId!!.toString()))
        return ResponseEntity.created(URI("/ecommerce/cart/${request.id}")).build()
    }

}