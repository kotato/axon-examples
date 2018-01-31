package com.kotato.context.ecommerce.modules.order.behaviour

import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewNotFoundException
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.domain.view.update.status.failed.OrderViewStatusAsFailedUpdater
import com.kotato.context.ecommerce.modules.order.domain.view.update.status.failed.UpdateOrderViewStatusOnOrderFailedEventHandler
import com.kotato.context.ecommerce.modules.order.domain.view.update.status.succeeded.OrderViewStatusAsSucceededUpdater
import com.kotato.context.ecommerce.modules.order.domain.view.update.status.succeeded.UpdateOrderViewStatusOnOrderSucceededEventHandler
import com.kotato.context.ecommerce.modules.order.stub.OrderFailedEventStub
import com.kotato.context.ecommerce.modules.order.stub.OrderIdStub
import com.kotato.context.ecommerce.modules.order.stub.OrderSucceededEventStub
import com.kotato.context.ecommerce.modules.order.stub.OrderViewStub
import com.kotato.shared.MockitoHelper.Companion.mockObject
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class OrderViewStatusAsSucceededUpdaterTest {

    private val repository: OrderViewRepository = mockObject()
    private val service = OrderViewStatusAsSucceededUpdater(repository)
    private val handler = UpdateOrderViewStatusOnOrderSucceededEventHandler(service)

    @Test
    fun `it should update order view status to succeeded`() {
        val view = OrderViewStub.random()
        val expected = view.copy(status = OrderStatus.SUCCEEDED)

        shouldSearchOrderView(view.id, view)
        shouldSaveOrderView(expected)

        handler.on(OrderSucceededEventStub.random(aggregateId = view.id.asString()))
    }

    @Test
    fun `it should throw exception when trying to update order view status to succeeded`() {
        val id = OrderIdStub.random()

        shouldSearchOrderView(id, null)

        assertFailsWith<OrderViewNotFoundException> {
            handler.on(OrderSucceededEventStub.random(aggregateId = id.asString()))
        }
    }

    private fun shouldSearchOrderView(id: OrderId, view: OrderView?) {
        doReturn(view).whenever(repository).search(id)
    }

    private fun shouldSaveOrderView(view: OrderView) {
        doNothing().whenever(repository).save(view)
    }

}