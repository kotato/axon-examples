package com.kotato.context.ecommerce.modules.cart.domain.view.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.transaction.ReadModelTransaction
import java.time.ZonedDateTime
import javax.inject.Named

@Named
open class CartViewCreator(private val repository: CartViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(id: CartId, createdAt: ZonedDateTime, userId: UserId) {
        CartView(id, createdAt, userId).let(repository::save)
    }

}