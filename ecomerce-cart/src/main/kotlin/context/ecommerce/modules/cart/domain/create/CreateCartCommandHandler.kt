package context.ecommerce.modules.cart.domain.create

import context.ecommerce.modules.cart.domain.CartId
import context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.CommandHandler
import javax.inject.Inject
import javax.inject.Named

@Named
open class CreateCartCommandHandler(@Inject private val creator: CartCreator) {

    @CommandHandler
    fun on(command: CreateCartCommand) {
        creator(CartId.fromString(command.id), UserId.fromString(command.userId))
    }

}