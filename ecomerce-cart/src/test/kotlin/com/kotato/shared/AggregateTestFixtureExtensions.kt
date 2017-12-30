package com.kotato.shared

import org.axonframework.eventhandling.EventMessage
import org.axonframework.eventhandling.GenericEventMessage
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.ResultValidator
import org.axonframework.test.aggregate.ResultValidatorImpl
import org.hamcrest.Matcher
import com.kotato.shared.MatcherAxonEventSimilar.Companion.similarAxonEventMatcher
import com.kotato.shared.domainevent.DomainEvent

fun ResultValidator.expectDomainEvent(event: DomainEvent): ResultValidator {
    return (this as ResultValidatorImpl).expectEventsMatching(similarAxonEventMatcher(event) as Matcher<out MutableList<in EventMessage<*>>>?)
}

private fun getUnitOfWork(): UnitOfWork<*> =
        try {
            CurrentUnitOfWork.get()
        } catch (e: IllegalStateException) {
            DefaultUnitOfWork.startAndGet<EventMessage<*>>(GenericEventMessage(""))
        }

fun <T> AggregateTestFixture<T>.loadAggregate(id: String): T =
        getUnitOfWork().let { uow -> repository.load(id).invoke { it }.also { uow.commit() } }