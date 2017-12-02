package com.kotato.shared.domainevent

interface DomainEvent {
    fun aggregateId(): String
}