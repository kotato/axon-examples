package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreator
import com.kotato.context.ecommerce.modules.cart.domain.create.CreateCartCommandHandler
import com.kotato.context.ecommerce.modules.cart.infrastructure.AxonCartRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartCreatedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartCommandStub
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CartCreatorTest {

    private val fixture = AggregateTestFixture(Cart::class.java)
    private val repository = AxonCartRepository(fixture.repository)
    private val service = CartCreator(repository)
    private val handler = CreateCartCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should create a cart`() {
        val command = CreateCartCommandStub.random()
        val expectedEvent = CartCreatedEventStub.random(aggregateId = command.id,
                                                        userId = command.userId)

        fixture.givenNoPriorActivity()
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expectedEvent)

        fixture.loadAggregate(command.id)
                .let { aggregate ->
                    assertEquals(CartId.fromString(command.id), aggregate.id)
                    assertEquals(UserId.fromString(command.userId), aggregate.userId)
                }

    }

}