package com.kotato.context.ecommerce.modules.cart.behaviour

import com.kotato.context.ecommerce.modules.cart.domain.Cart
import com.kotato.context.ecommerce.modules.cart.domain.CartNotFoundException
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartAlreadyCheckoutException
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartCheckedOutEvent
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartCheckout
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CartIsEmptyException
import com.kotato.context.ecommerce.modules.cart.domain.checkout.CheckoutCartCommandHandler
import com.kotato.context.ecommerce.modules.cart.infrastructure.AxonCartRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartCheckedOutEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartCreatedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CartItemAddedEventStub
import com.kotato.context.ecommerce.modules.cart.stub.CheckoutCartCommandStub
import com.kotato.shared.expectDomainEvent
import com.kotato.shared.loadAggregate
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import kotlin.test.assertTrue

class CartCheckoutTest {
    private val fixture = AggregateTestFixture(Cart::class.java)
    private val repository = AxonCartRepository(fixture.repository)
    private val service = CartCheckout(repository)
    private val handler = CheckoutCartCommandHandler(service)

    @BeforeEach
    fun setUp() {
        fixture.registerAnnotatedCommandHandler(handler)
    }

    @Test
    fun `it should checkout a cart with two items`() {
        val command = CheckoutCartCommandStub.random()
        val expected = CartCheckedOutEvent(command.cartId,
                                           ZonedDateTime.now(),
                                           command.orderId)
        val givenCartCreated = CartCreatedEventStub.random(aggregateId = command.cartId)
        val givenAddCartItem1 = CartItemAddedEventStub.random(aggregateId = command.cartId)
        val givenAddCartItem2 = CartItemAddedEventStub.random(aggregateId = command.cartId)
        fixture.given(givenCartCreated, givenAddCartItem1, givenAddCartItem2)
                .`when`(command)
                .expectSuccessfulHandlerExecution()
                .expectDomainEvent(expected)

        fixture.loadAggregate(command.cartId)
                .let { assertTrue { it.checkout } }
    }

    @Test
    fun `it should throw exception when try to checkout a cart that doesn't exist`() {
        val command = CheckoutCartCommandStub.random()
        fixture.givenNoPriorActivity()
                .`when`(command)
                .expectException(CartNotFoundException::class.java)
    }

    @Test
    fun `it should throw exception when try to checkout an empty cart`() {
        val command = CheckoutCartCommandStub.random()
        val givenCartCreated = CartCreatedEventStub.random(aggregateId = command.cartId)
        fixture.given(givenCartCreated)
                .`when`(command)
                .expectException(CartIsEmptyException::class.java)
    }

    @Test
    fun `it should throw exception when try to checkout a cart checked out`() {
        val command = CheckoutCartCommandStub.random()
        val givenCartCreated = CartCreatedEventStub.random(aggregateId = command.cartId)
        val givenAddCartItem = CartItemAddedEventStub.random(aggregateId = command.cartId)
        val givenCheckedOut = CartCheckedOutEventStub.random(aggregateId = command.cartId)
        fixture.given(givenCartCreated, givenAddCartItem, givenCheckedOut)
                .`when`(command)
                .expectException(CartAlreadyCheckoutException::class.java)
    }
}