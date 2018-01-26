package com.kotato.context.ecommerce.modules.order.infrastructure.view

import com.kotato.context.ecommerce.modules.order.behaviour.OrderId
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderView
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderViewRepository
import org.springframework.data.jpa.repository.JpaRepository
import javax.inject.Named

@Named
open class JpaOrderViewRepository(private val persistenceRepository: JpaOrderViewPersistenceRepository) : OrderViewRepository {

    override fun save(view: OrderView) {
        view.let(persistenceRepository::saveAndFlush)
    }

    override fun search(id: OrderId): OrderView? = id.let(persistenceRepository::findOne)

    interface JpaOrderViewPersistenceRepository : JpaRepository<OrderView, OrderId>

}