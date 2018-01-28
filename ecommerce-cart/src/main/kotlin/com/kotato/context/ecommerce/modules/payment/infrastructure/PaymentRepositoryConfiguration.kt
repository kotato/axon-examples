package com.kotato.context.ecommerce.modules.payment.infrastructure

import com.kotato.context.ecommerce.modules.payment.domain.Payment
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
open class PaymentRepositoryConfiguration {

    @Bean
    open fun paymentAggregateFactory(): AggregateFactory<Payment> =
            SpringPrototypeAggregateFactory<Payment>().also {
                it.setPrototypeBeanName(Payment::class.simpleName!!.toLowerCase())
            }

    @Bean
    open fun paymentRepository(snapshotter: Snapshotter,
                             eventStore: EventStore,
                             cache: Cache): Repository<Payment> =
            CachingEventSourcingRepository(paymentAggregateFactory(), eventStore, cache)
}