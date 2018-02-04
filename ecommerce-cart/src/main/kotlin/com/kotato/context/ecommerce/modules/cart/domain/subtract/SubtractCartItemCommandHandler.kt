package com.kotato.context.ecommerce.modules.cart.domain.subtract

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.cqrs.domain.command.CommandHandler
import com.kotato.shared.money.Money
import javax.inject.Named

@Named
open class SubtractCartItemCommandHandler(private val subtractor: CartItemSubtractor) {

    @CommandHandler
    fun on(command: SubtractCartItemCommand) {
        subtractor(CartId.fromString(command.id), command.toCartItem(), command.quantity)
    }

    private fun SubtractCartItemCommand.toCartItem() =
            CartItem(itemId = ItemId.fromString(itemId),
                     price = Money.of(price, currency))


}