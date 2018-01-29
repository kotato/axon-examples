package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.add.AddCartItemCommandHandler
import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAddedEvent
import com.kotato.context.ecommerce.modules.cart.domain.add.CartItemAdder
import com.kotato.context.ecommerce.modules.cart.infrastructure.AxonCartRepository
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemCommandStub
import com.kotato.context.ecommerce.modules.cart.stub.CartCreatedEventStub
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import kotlin.test.assertTrue

class CartItemAdderTest {
    private val fixture = AggregateTestFixture(Cart::class.java)
    private val repository = AxonCartRepository(fixture.repository)
    private val service = CartItemAdder(repository)
    private val handler = AddCartItemCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should add a cart item`() {
        val command = AddCartItemCommandStub.random()
        val expected = CartItemAddedEvent(command.cartId,
                                          ZonedDateTime.now(),
                                          command.itemId,
                                          command.quantity,
                                          command.price,
                                          command.currency)
        fixture.given(CartCreatedEventStub.random(aggregateId = command.cartId))
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.cartId)
                .let { assertTrue { it.cartItems.size == 1 } }
    }

    @Test
    fun `it should add a cart item on already existing item on cart`() {
        val command = AddCartItemCommandStub.random()
        val givenCommand = AddCartItemCommandStub.random(id = command.cartId,
                                                         itemId = command.itemId,
                                                         currency = command.currency,
                                                         price = command.price)
        val expected = CartItemAddedEvent(command.cartId,
                                          ZonedDateTime.now(),
                                          command.itemId,
                                          command.quantity,
                                          command.price,
                                          command.currency)
        fixture.given(CartCreatedEventStub.random(aggregateId = command.cartId))
                .andGivenCommands(givenCommand)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.cartId)
                .let {
                    assertTrue { it.cartItems.size == 1 }
                    assertTrue { it.cartItems[it.cartItems.keys.first()]!!.amount == (command.quantity + givenCommand.quantity) }
                }
    }

    @Test
    fun `it should add a cart item on already existing item on cart with different price`() {
        val command = AddCartItemCommandStub.random()
        val givenCommand = AddCartItemCommandStub.random(id = command.cartId,
                                                         itemId = command.itemId,
                                                         currency = command.currency)
        val expected = CartItemAddedEvent(command.cartId,
                                          ZonedDateTime.now(),
                                          command.itemId,
                                          command.quantity,
                                          command.price,
                                          command.currency)
        fixture.given(CartCreatedEventStub.random(aggregateId = command.cartId))
                .andGivenCommands(givenCommand)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.cartId)
                .let {
                    assertTrue { it.cartItems.size == 2 }
                }
    }

    @Test
    fun `it should throw exception when trying to add a cart item`() {
        fixture.givenNoPriorActivity()
                .`when`(AddCartItemCommandStub.random())
                .expectException(CartNotFoundException::class.java)
    }
}