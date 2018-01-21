package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.shared.money.Money
import com.kotato.shared.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
@ValueObject
data class CartItem(@Embedded val itemId: ItemId,
                    @Embedded val price: Money)