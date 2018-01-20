package com.kotato.context.ecommerce.modules.cart.domain

typealias CartItems = List<CartItem>

fun CartItems.add(cartItem: CartItem) =
        if (this.notHasItem(cartItem)) {
            listOf(cartItem, *this.toTypedArray())
        } else {
            listOf(
                    *this.filterNot { it.similar(cartItem) }.toTypedArray(),
                    this.first { it.similar(cartItem) }.let { it.copy(quantity = it.quantity + cartItem.quantity) })
        }

private fun CartItems.notHasItem(cartItem: CartItem) =
        this.none { it.itemId == cartItem.itemId && it.price == cartItem.price }

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
private fun CartItem.similar(cartItem: CartItem) =
        this.itemId == cartItem.itemId && this.price == cartItem.price