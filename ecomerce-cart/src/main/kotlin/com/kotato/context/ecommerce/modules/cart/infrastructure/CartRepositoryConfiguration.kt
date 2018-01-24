package com.kotato.context.ecommerce.modules.cart.infrastructure

import com.kotato.context.ecommerce.modules.cart.domain.Cart
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
open class CartRepositoryConfiguration {

    @Bean
    open fun cartAggregateFactory(): AggregateFactory<Cart> =
            SpringPrototypeAggregateFactory<Cart>().also {
                it.setPrototypeBeanName(Cart::class.simpleName!!.toLowerCase())
            }

    @Bean
    open fun cartRepository(snapshotter: Snapshotter,
                            eventStore: EventStore,
                            cache: Cache): Repository<Cart> =
            CachingEventSourcingRepository(cartAggregateFactory(), eventStore, cache)
}