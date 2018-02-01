package com.kotato.context.ecommerce.modules.order.acceptance

import com.kotato.assertSimilar.MatcherSimilar
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.shared.ContextStarterTest
import org.junit.jupiter.api.Test
import javax.inject.Inject
import kotlin.test.assertNotNull

class OrderFailedTest : ContextStarterTest() {

    @Inject private lateinit var repository: OrderViewRepository

    @Test
    fun `it should create an order`() {
        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)
        cartFlow.checkout(cartId)

        Thread.sleep(200)

        val paymentId = repository.searchByCartId(cartId)
                .also { assertNotNull(it) }!!
                .paymentId

        paymentFlow.failedCallback(paymentId)

        Thread.sleep(200)

        repository.searchByCartId(cartId)
                .also { assertNotNull(it) }!!
                .let { MatcherSimilar.assertSimilar(it.status, OrderStatus.FAILED) }

    }

}