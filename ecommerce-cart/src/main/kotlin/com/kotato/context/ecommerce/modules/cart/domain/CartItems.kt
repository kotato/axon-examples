package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext

@ValueObject
data class Amount(val amount: Int) : Serializable {

    operator fun plus(other: Amount) = Amount(this.amount + other.amount)
    operator fun minus(other: Amount) = Amount(this.amount - other.amount)

}

typealias CartItems = Map<CartItem, Amount>
typealias SerializedCartItems = Map<Triple<String, String, String>, Int>

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

fun SerializedCartItems.toCartItems() =
        this.mapKeys {
            CartItem(ItemId.fromString(it.key.first),
                     Money.of(BigDecimal(it.key.second, MathContext.UNLIMITED),
                              it.key.third))
        }.mapValues { Amount(it.value) }

fun CartItems.toSerializedCartItems() =
        this.mapKeys {
            it.key.let {
                Triple(it.itemId.asString(),
                       it.price.amount.toPlainString(),
                       it.price.currency)
            }
        }.mapValues { it.value.amount }