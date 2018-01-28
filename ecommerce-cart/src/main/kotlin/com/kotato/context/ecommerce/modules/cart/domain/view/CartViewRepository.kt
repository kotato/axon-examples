package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId

interface CartViewRepository {

    fun save(entity: CartView)
    fun search(id: CartId): CartView?

}