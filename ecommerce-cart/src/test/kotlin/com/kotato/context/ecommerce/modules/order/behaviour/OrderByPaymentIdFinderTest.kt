package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewByPaymentIdNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.FindOrderByPaymentIdQuery
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.FindOrderByPaymentIdQueryHandler
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_payment_id.OrderByPaymentIdFinder
import com.kotato.context.ecommerce.modules.order.domain.view.update.status.succeeded.OrderViewStatusAsSucceededUpdater
import com.kotato.context.ecommerce.modules.order.stub.OrderViewStub
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.stub.PaymentIdStub
import com.kotato.shared.MockitoHelper.Companion.mockObject
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class OrderByPaymentIdFinderTest {

    private val repository: OrderViewRepository = mockObject()
    private val service = OrderByPaymentIdFinder(repository)
    private val handler = FindOrderByPaymentIdQueryHandler(service)

    @Test
    fun `it should find order by payment id`() {
        val view = OrderViewStub.random()
        val response = OrderResponse(view.id.asString(),
                                     view.createdOn,
                                     view.userId.asString(),
                                     view.cartId.asString(),
                                     view.paymentId.asString(),
                                     view.status.name,
                                     view.cartItems.toSerializedCartItems())
        val query = FindOrderByPaymentIdQuery(view.paymentId.asString())

        shouldSearchOrderView(view.paymentId, view)

        assertSimilar(handler.on(query), response)
    }

    @Test
    fun `it should throw exception when trying to find order by payment id`() {
        val paymentId = PaymentIdStub.random()

        shouldSearchOrderView(paymentId, null)

        assertFailsWith<OrderViewByPaymentIdNotFoundException> {
            handler.on(FindOrderByPaymentIdQuery(paymentId.asString()))
        }
    }

    private fun shouldSearchOrderView(id: PaymentId, view: OrderView?) {
        doReturn(view).whenever(repository).searchByPaymentId(id)
    }

}