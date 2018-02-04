package com.kotato.context.ecommerce.modules.order.domain.view.find.by_id

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import javax.inject.Named

@Named
open class OrderFinder(private val repository: OrderViewRepository) {

    operator fun invoke(id: OrderId) =
            repository.search(id).also { guardOrderExists(id, it) }!!

    private fun guardOrderExists(id: OrderId, order: OrderView?) {
        order ?: throw OrderViewNotFoundException(id.asString())
    }

}