package com.kotato.shared.domainevent

import java.time.ZonedDateTime

interface DomainEvent {
    fun aggregateId(): String
    fun occurredOn(): ZonedDateTime
}