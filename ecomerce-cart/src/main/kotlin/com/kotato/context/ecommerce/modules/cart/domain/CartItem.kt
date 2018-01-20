package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.item.domain.ItemId
import org.javamoney.moneta.Money
import javax.persistence.Embeddable

@Embeddable
data class CartItem(val itemId: ItemId,
                    val price: Money)