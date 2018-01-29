package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemSubtractedEvent
import com.kotato.context.ecommerce.modules.cart.domain.subtract.CartItemSubtractor
import com.kotato.context.ecommerce.modules.cart.domain.subtract.SubtractCartItemCommand
import com.kotato.context.ecommerce.modules.cart.domain.subtract.SubtractCartItemCommandHandler
import com.kotato.context.ecommerce.modules.cart.infrastructure.AxonCartRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartCreatedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartItemAddedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.SubtractCartItemCommandStub
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import kotlin.test.assertTrue

class CartItemSubtractorTest {
    private val fixture = AggregateTestFixture(Cart::class.java)
    private val repository = AxonCartRepository(fixture.repository)
    private val service = CartItemSubtractor(repository)
    private val handler = SubtractCartItemCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should subtract a cart item`() {
        val givenCartCreated = CartCreatedEventStub.random()
        val givenAddCartItem = CartItemAddedEventStub.random(aggregateId = givenCartCreated.aggregateId(),
                                                             quantity = 2)
        val command = SubtractCartItemCommand(id = givenCartCreated.aggregateId(),
                                              itemId = givenAddCartItem.itemId,
                                              quantity = givenAddCartItem.quantity - 1,
                                              price = givenAddCartItem.price,
                                              currency = givenAddCartItem.currency)
        val expected = CartItemSubtractedEvent(command.id,
                                               ZonedDateTime.now(),
                                               command.itemId,
                                               command.quantity,
                                               command.price,
                                               command.currency)
        fixture.given(givenCartCreated, givenAddCartItem)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.id)
                .let {
                    assertTrue { it.cartItems.size == 1 }
                    assertTrue { it.cartItems.let { it[it.keys.first()] }!!.amount == 1 }
                }
    }

    @Test
    fun `it should subtract a cart item with quantity greater than amount in cart`() {
        val givenCartCreated = CartCreatedEventStub.random()
        val givenAddCartItem = CartItemAddedEventStub.random(aggregateId = givenCartCreated.aggregateId(),
                                                             quantity = 2)
        val command = SubtractCartItemCommand(id = givenCartCreated.aggregateId(),
                                              itemId = givenAddCartItem.itemId,
                                              quantity = givenAddCartItem.quantity + 1,
                                              price = givenAddCartItem.price,
                                              currency = givenAddCartItem.currency)
        val expected = CartItemSubtractedEvent(command.id,
                                               ZonedDateTime.now(),
                                               command.itemId,
                                               givenAddCartItem.quantity,
                                               command.price,
                                               command.currency)
        fixture.given(givenCartCreated, givenAddCartItem)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.id)
                .let { assertTrue { it.cartItems.isEmpty() } }
    }

    @Test
    fun `it should subtract a cart item with quantity equal to amount in cart`() {
        val givenCartCreated = CartCreatedEventStub.random()
        val givenAddCartItem = CartItemAddedEventStub.random(aggregateId = givenCartCreated.aggregateId(),
                                                             quantity = 2)
        val command = SubtractCartItemCommand(id = givenCartCreated.aggregateId(),
                                              itemId = givenAddCartItem.itemId,
                                              quantity = givenAddCartItem.quantity,
                                              price = givenAddCartItem.price,
                                              currency = givenAddCartItem.currency)
        val expected = CartItemSubtractedEvent(command.id,
                                               ZonedDateTime.now(),
                                               command.itemId,
                                               command.quantity,
                                               command.price,
                                               command.currency)
        fixture.given(givenCartCreated, givenAddCartItem)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.id)
                .let { assertTrue { it.cartItems.isEmpty() } }
    }

    @Test
    fun `it should throw exception when trying to subtract a cart item that is not in cart`() {
        CartCreatedEventStub.random().let {
            fixture.given()
                    .`when`(SubtractCartItemCommandStub.random(id = it.aggregateId()))
                    .expectException(CartNotFoundException::class.java)
        }

    }

    @Test
    fun `it should throw exception when trying to subtract a cart item`() {
        fixture.givenNoPriorActivity()
                .`when`(SubtractCartItemCommandStub.random())
                .expectException(CartNotFoundException::class.java)
    }
}