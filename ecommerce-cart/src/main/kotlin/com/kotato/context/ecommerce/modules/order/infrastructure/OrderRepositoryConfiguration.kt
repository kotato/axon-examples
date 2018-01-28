package com.kotato.context.ecommerce.modules.order.infrastructure

import com.kotato.context.ecommerce.modules.order.domain.Order
import org.axonframework.commandhandling.model.Repository
import org.axonframework.common.caching.Cache
import org.axonframework.eventsourcing.AggregateFactory
import org.axonframework.eventsourcing.CachingEventSourcingRepository
import org.axonframework.eventsourcing.Snapshotter
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class OrderRepositoryConfiguration {

    @Bean
    open fun orderAggregateFactory(): AggregateFactory<Order> =
            SpringPrototypeAggregateFactory<Order>().also {
                it.setPrototypeBeanName(Order::class.simpleName!!.toLowerCase())
            }

    @Bean
    open fun orderRepository(snapshotter: Snapshotter,
                             eventStore: EventStore,
                             cache: Cache): Repository<Order> =
            CachingEventSourcingRepository(orderAggregateFactory(), eventStore, cache)
}