package com.kotato.context.ecommerce.modules.payment.acceptance

import com.kotato.assertSimilar.MatcherSimilar
import com.kotato.context.ecommerce.modules.cart.stub.AddCartItemRestRequestStub
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CreateCartRestRequestStub
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.context.ecommerce.modules.payment.stub.PaymentIdStub
import com.kotato.shared.ContextStarterTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.inject.Inject
import kotlin.test.assertNotNull

class PaymentSucceedCallbackControllerTest : ContextStarterTest() {

    @Inject private lateinit var orderRepository: OrderViewRepository
    @Inject private lateinit var paymentRepository: PaymentViewRepository

    @Test
    fun `it should update payment as succeeded`() {
        val cartId = CartIdStub.random()
        val createCart = CreateCartRestRequestStub.random(cartId.id)
        val addItem = AddCartItemRestRequestStub.random()
        cartFlow.createCart(createCart)
        cartFlow.addItem(addItem, cartId)
        cartFlow.checkout(cartId)

        Thread.sleep(200)

        val paymentId = orderRepository.searchByCartId(cartId).also { assertNotNull(it) }!!.paymentId

        postCallback(paymentId.asString())
                .statusCode(HttpStatus.NO_CONTENT.value())

        paymentRepository.search(paymentId)
                .also { assertNotNull(it) }!!
                .let { MatcherSimilar.assertSimilar(it.status, PaymentStatus.SUCCEEDED) }
    }

    @Test
    fun `it should throw exception when trying to update payment status`() {
        postCallback(PaymentIdStub.random().asString())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    private fun postCallback(id: String) =
            RestAssured.given()
                    .header("Content-Type", "application/json")
                    .`when`()
                    .post("/ecommerce/payment/$id/succeeded")
                    .then()
}