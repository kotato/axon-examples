package context.ecommerce.modules.cart.domain.create

import cqrs.domain.command.Command


data class CreateCartCommand(val id: String, val userId: String): Command