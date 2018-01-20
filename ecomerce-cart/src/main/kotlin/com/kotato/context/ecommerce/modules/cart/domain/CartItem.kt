package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.item.domain.ItemId
import org.javamoney.moneta.Money

data class CartItem(val itemId: ItemId, val quantity: Int, val price: Money)