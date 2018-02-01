package com.kotato.context.ecommerce.modules.payment.acceptance

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.adapter.update.CartItemRestRequest
import com.kotato.context.ecommerce.modules.cart.domain.Amount
import com.kotato.context.ecommerce.modules.cart.domain.CartItem
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.item.domain.ItemId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.context.ecommerce.modules.payment.stub.PaymentViewStub
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.money.Money
import org.junit.jupiter.api.Test
import javax.inject.Inject
import kotlin.test.assertNotNull

class CreatePaymentTest : ContextStarterTest() {

    @Inject private lateinit var orderRepository: OrderViewRepository
    @Inject private lateinit var paymentRepository: PaymentViewRepository

    @Test
    fun `it should create an order`() {

        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)
        cartFlow.checkout(cartId)

        Thread.sleep(200)

        val paymentId = orderRepository.searchByCartId(cartId).also { assertNotNull(it) }!!.paymentId
        paymentRepository.search(paymentId)
                .also { assertNotNull(it) }!!
                .let {
                    assertSimilar(it, PaymentViewStub.random(
                            id = paymentId,
                            price = Money.of(addItem.price!!, addItem.currency!!) * Amount(addItem.quantity!!),
                            status = PaymentStatus.PENDING))
                }


    }
}