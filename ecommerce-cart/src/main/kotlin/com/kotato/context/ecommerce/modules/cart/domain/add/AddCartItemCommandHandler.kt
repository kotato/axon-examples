package com.kotato.context.ecommerce.modules.cart.domain.add

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.cqrs.domain.command.CommandHandler
import com.kotato.shared.money.Money
import javax.inject.Named

@Named
open class AddCartItemCommandHandler(private val adder: CartItemAdder) {

    @CommandHandler
    fun on(command: AddCartItemCommand) {
        adder(CartId.fromString(command.cartId), command.toCartItem(), command.quantity)
    }

    private fun AddCartItemCommand.toCartItem() =
            CartItem(itemId = ItemId.fromString(itemId),
                     price = Money.of(price, currency))

}