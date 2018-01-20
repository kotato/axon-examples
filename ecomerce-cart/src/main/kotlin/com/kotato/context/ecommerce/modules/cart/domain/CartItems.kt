package com.kotato.context.ecommerce.modules.cart.domain

typealias CartItems = Map<CartItem, Int>

fun CartItems.add(cartItem: CartItem, quantity: Int) =
        if (this.notHasItem(cartItem)) {
            mapOf(cartItem to quantity, *this.pairs())
        } else {
            mapOf(*this.filterNot { it.key.similar(cartItem) }.pairs(),
                  this.filter { it.key.similar(cartItem) }
                          .let { it.keys.first() to (it[it.keys.first()]!! + quantity) })
        }

fun CartItems.subtract(cartItem: CartItem, quantity: Int) =
        if (this.notHasItem(cartItem)) {
            TODO("THROW EXCEPTION HERE")
        } else if (this.filter { it.key.similar(cartItem) }.let { it[it.keys.first()]!! <= quantity }) {
            mapOf(*this.filterNot { it.key.similar(cartItem) }.pairs())
        } else {
            mapOf(*this.filterNot { it.key.similar(cartItem) }.pairs(),
                  this.filter { it.key.similar(cartItem) }
                          .let { it.keys.first() to (it[it.keys.first()]!! - quantity) })
        }

private fun CartItems.notHasItem(cartItem: CartItem) =
        this.keys.none { it.itemId == cartItem.itemId && it.price == cartItem.price }

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
private fun CartItem.similar(cartItem: CartItem) =
        this.itemId == cartItem.itemId && this.price == cartItem.price

private fun CartItems.pairs() = this.entries.map { it.key to it.value }.toTypedArray()