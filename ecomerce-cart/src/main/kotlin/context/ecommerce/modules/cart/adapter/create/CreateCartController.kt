package context.ecommerce.modules.cart.adapter.create

import context.ecommerce.modules.cart.domain.create.CreateCartCommand
import cqrs.domain.command.CommandBus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.validation.Valid

@RestController
open class CreateCartController(@Inject val commandBus: CommandBus) {

    @PostMapping("/ecommerce/cart")
    open fun potato(@RequestBody @Valid request: CreateCartRequest) {
        commandBus.handle(CreateCartCommand(request.id!!.toString(),
                                            request.userId!!.toString()))
    }

}