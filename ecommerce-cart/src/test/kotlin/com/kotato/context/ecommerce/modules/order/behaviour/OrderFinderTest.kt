package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_id.FindOrderQuery
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_id.FindOrderQueryHandler
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_id.OrderFinder
import com.kotato.context.ecommerce.modules.order.stub.OrderIdStub
import com.kotato.context.ecommerce.modules.order.stub.OrderViewStub
import com.kotato.shared.MockitoHelper.Companion.mockObject
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class OrderFinderTest {

    private val repository: OrderViewRepository = mockObject()
    private val service = OrderFinder(repository)
    private val handler = FindOrderQueryHandler(service)

    @Test
    fun `it should find an order`() {
        val view = OrderViewStub.random()
        val query = FindOrderQuery(view.id.asString())
        val expected = OrderResponse(view.id.asString(),
                                     view.createdOn,
                                     view.userId.asString(),
                                     view.cartId.asString(),
                                     view.paymentId.asString(),
                                     view.status.name,
                                     view.cartItems.toSerializedCartItems())

        shouldSearchOrder(view.id, view)

        assertSimilar(handler.on(query), expected)
    }

    @Test
    fun `it should throw exception when finding an order`() {
        val id = OrderIdStub.random()
        val query = FindOrderQuery(id.asString())

        shouldSearchOrder(id, null)

        assertFailsWith<OrderViewNotFoundException> {
            handler.on(FindOrderQuery(id.asString()))
        }
    }

    private fun shouldSearchOrder(id: OrderId, order: OrderView?) {
        doReturn(order).whenever(repository).search(id)
    }

}