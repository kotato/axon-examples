package com.kotato.shared

import com.kotato.assertSimilar.Similar
import org.axonframework.eventsourcing.GenericDomainEventMessage
import org.axonframework.test.matchers.EqualFieldsMatcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Factory
import org.hamcrest.Matcher
import com.kotato.shared.domainevent.DomainEvent

class AxonEventSimilar<T> private constructor(private val matcher: Matcher<*>) : BaseMatcher<T>() {

    override fun matches(item: Any): Boolean =
            (if (item is List<*> &&
                 item.size != 0 &&
                 item[0] is GenericDomainEventMessage<*>) payloads(item).first()
            else item).let { matcher.matches(it) }

    private fun payloads(item: List<*>): List<DomainEvent> =
            item.map { event -> (event as GenericDomainEventMessage<*>).payload } as List<DomainEvent>

    override fun describeTo(description: Description) {
        matcher.describeTo(description)
    }

    companion object {
        @Factory
        internal fun <T> axonSimilar(target: T): Matcher<out Matcher<*>> {
            return AxonEventSimilar(Similar.similar(target))
        }
    }
}
