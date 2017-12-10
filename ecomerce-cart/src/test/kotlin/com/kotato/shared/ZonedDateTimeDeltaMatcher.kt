package com.kotato.shared

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import java.time.ZonedDateTime

class ZonedDateTimeDeltaMatcher(val expected: ZonedDateTime, val delta: Int = DELTA) : BaseMatcher<ZonedDateTime>() {

    override fun matches(item: Any?): Boolean =
            expected.toEpochSecond() - (item as ZonedDateTime).toEpochSecond() < delta


    override fun describeTo(description: Description) {}

    companion object {
        private const val DELTA: Int = 2000

        fun matches(expected: Any?, actual: Any?): Boolean =
                ZonedDateTimeDeltaMatcher.matches(expected, actual, DELTA)

        fun matches(expected: Any?, actual: Any?, delta: Int): Boolean =
                null != expected && null != actual &&
                ZonedDateTime::class.let { it.isInstance(expected) && it.isInstance(actual) } &&
                ZonedDateTimeDeltaMatcher(expected as ZonedDateTime, delta).matches(actual)
    }

}