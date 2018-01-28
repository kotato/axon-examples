package com.kotato.context.ecommerce.modules.cart.domain.create

import com.kotato.cqrs.domain.command.Command


data class CreateCartCommand(val id: String, val userId: String) : Command