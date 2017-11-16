package shared.domainevent

interface DomainEvent {
    fun aggregateId(): String
}