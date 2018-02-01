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
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.test.aggregate.TestExecutor
import org.axonframework.test.matchers.FieldFilter
import org.axonframework.test.matchers.MatchAllFieldFilter
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaConstructor

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

fun ResultValidator.getPublishedEvents(): List<DomainEvent> {
    val property = ResultValidatorImpl::class.memberProperties.filter { it.name == "publishedEvents" }.first()
    property.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return (property.getter.call(this as ResultValidatorImpl) as List<EventMessage<*>>).map { it.payload as DomainEvent }.toList()
}

fun TestExecutor.whenLambda(lambda: () -> Unit): ResultValidator {
    return this.whenLambda(lambda, null)
}

fun TestExecutor.whenLambda(lambda: () -> Unit, expectedException: KClass<out Throwable>? = null): ResultValidator {

    val uow = getUnitOfWork()

    val klass = AggregateTestFixture::class.nestedClasses.filter { it.simpleName == "AggregateRegisteringInterceptor" }.first()
    val klassConstructor = klass.constructors.first()
    klassConstructor.isAccessible = true

    @Suppress("UNCHECKED_CAST")
    val aggInterceptor = klassConstructor.javaConstructor!!.newInstance(this) as MessageHandlerInterceptor<CommandMessage<*>>
    val property = AggregateTestFixture::class.memberProperties.filter { it.name == "commandHandlerInterceptors" }.first()
    property.isAccessible = true

    @Suppress("UNCHECKED_CAST")
    (property.getter.call(this as AggregateTestFixture<*>) as MutableList<MessageHandlerInterceptor<CommandMessage<*>>>).add(
            aggInterceptor)

    val finalizeConfiguration = AggregateTestFixture::class.memberFunctions.filter { it.name == "finalizeConfiguration" }.first()
    finalizeConfiguration.isAccessible = true
    finalizeConfiguration.call(this)

    val propertyFieldFilters = AggregateTestFixture::class.memberProperties.filter { it.name == "fieldFilters" }.first()
    propertyFieldFilters.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    val fieldFilter = MatchAllFieldFilter(propertyFieldFilters.getter.call(this) as MutableList<FieldFilter>)

    @Suppress("UNCHECKED_CAST")
    val propertyPublishedEvents = AggregateTestFixture::class.memberProperties.filter { it.name == "publishedEvents" }.first() as KMutableProperty<List<EventMessage<*>>>
    propertyPublishedEvents.isAccessible = true
    val publishedEvents: List<EventMessage<*>> = propertyPublishedEvents.getter.call(this)

    val resultValidator = ResultValidatorImpl(publishedEvents, fieldFilter)

    if (expectedException != null) executeLambdaExpectingException(lambda, expectedException) else lambda()

    val detectFun = AggregateTestFixture::class.memberFunctions.filter { it.name == "detectIllegalStateChanges" }.first()
    detectFun.isAccessible = true
    detectFun.call(this, fieldFilter)

    uow.commit()

    resultValidator.assertValidRecording()
    return resultValidator
}

private fun executeLambdaExpectingException(lambda: () -> Unit, expectedException: KClass<out Throwable>) {
    try {
        lambda()
    } catch (e: Exception) {
        if (!expectedException.isInstance(e)) {
            throw RuntimeException("Expected exception of type <${expectedException.simpleName}> but got <${e::class.simpleName}> instead.")
        }
        return
    }
    throw RuntimeException("Expected exception of type <${expectedException.simpleName}> but nothing were raised.")
}