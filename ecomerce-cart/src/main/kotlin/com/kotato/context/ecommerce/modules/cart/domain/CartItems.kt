package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable

@ValueObject
data class Amount(val amount: Int): Serializable {

    operator fun plus(other: Amount) = Amount(this.amount + other.amount)
    operator fun minus(other: Amount) = Amount(this.amount - other.amount)

}

typealias CartItems = Map<CartItem, Amount>

fun CartItems.add(cartItem: CartItem, quantity: Int) =
        if (this.notHasItem(cartItem)) {
            mapOf(cartItem to Amount(quantity), *this.pairs())
        } else {
            mapOf(*this.filterNot { it.key == cartItem }.pairs(),
                  this.filter { it.key == cartItem }
                          .let { it.keys.first() to (it[it.keys.first()]!! + Amount(quantity)) })
        }

fun CartItems.subtract(cartItem: CartItem, quantity: Int) =
        if (this.filter { it.key == cartItem }.let { it[it.keys.first()]!!.amount <= quantity }) {
            mapOf(*this.filterNot { it.key == cartItem }.pairs())
        } else {
            mapOf(*this.filterNot { it.key == cartItem }.pairs(),
                  this.filter { it.key == cartItem }
                          .let { it.keys.first() to (it[it.keys.first()]!! - Amount(quantity)) })
        }

private fun CartItems.notHasItem(cartItem: CartItem) =
        this.keys.none { it.itemId == cartItem.itemId && it.price == cartItem.price }

private fun CartItems.pairs() = this.entries.map { it.key to it.value }.toTypedArray()