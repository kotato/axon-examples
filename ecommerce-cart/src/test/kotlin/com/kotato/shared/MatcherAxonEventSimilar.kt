package com.kotato.shared

import org.axonframework.eventhandling.EventMessage
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat

class MatcherAxonEventSimilar {
    companion object {
        fun <T> assertAxonEventSimilar(actual: T, expected: T) {
            assertAxonEventSimilar("", actual, expected)
        }

        fun <T> assertAxonEventSimilar(reason: String, actual: T, expected: T) {
            assertThat(reason, actual, AxonEventSimilar.axonSimilar(expected) as Matcher<T>)
        }

        fun <T> similarAxonEventMatcher(expected: T): Matcher<out MutableList<in EventMessage<*>>> {
            return AxonEventSimilar.axonSimilar(expected) as Matcher<out MutableList<in EventMessage<*>>>
        }
    }
}
