package com.kotato.context.ecommerce.modules.order.integration

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.context.ecommerce.modules.order.behaviour.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.order.stub.OrderViewStub
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.TransactionalWrapper
import org.junit.jupiter.api.Test
import javax.inject.Inject

open class OrderViewRepositoryTest : ContextStarterTest() {

    @Inject private lateinit var repository: OrderViewRepository
    @Inject private lateinit var wrapper: TransactionalWrapper

    @Test
    open fun `it should save order view`() {
        val view = OrderViewStub.random()

        wrapper.wrap { repository.save(view) }

        assertSimilar(view, repository.search(view.id))
    }

    @Test
    open fun `it should search order view by cartId`() {
        val view = OrderViewStub.random()

        wrapper.wrap { repository.save(view) }

        assertSimilar(view, repository.searchByCartId(view.cartId))
    }

}