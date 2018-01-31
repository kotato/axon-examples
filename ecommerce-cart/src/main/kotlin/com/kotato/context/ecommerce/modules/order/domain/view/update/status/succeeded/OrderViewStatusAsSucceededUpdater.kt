package com.kotato.context.ecommerce.modules.order.domain.view.update.status.succeeded

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class OrderViewStatusAsSucceededUpdater(private val repository: OrderViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(id: OrderId) {
        repository.search(id)
                .also { guardOrderExists(id, it) }!!
                .updateAsSucceeded()
                .let(repository::save)
    }

    private fun guardOrderExists(id: OrderId, order: OrderView?) {
        order ?: throw OrderViewNotFoundException(id.asString())
    }

}