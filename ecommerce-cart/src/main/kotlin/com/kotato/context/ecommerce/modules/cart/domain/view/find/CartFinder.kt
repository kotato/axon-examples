package com.kotato.context.ecommerce.modules.cart.domain.view.find

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import javax.inject.Named

@Named
open class CartFinder(private val repository: CartViewRepository) {

    operator fun invoke(id: CartId) =
            repository.search(id).also { guardCartExists(id, it) }!!

    private fun guardCartExists(id: CartId, entity: CartView?) {
        entity ?: throw CartViewNotFoundException(id.asString())
    }

}