package com.kotato.context.ecommerce.modules.cart.integration

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.shared.ContextStarterTest
import com.kotato.shared.TransactionalWrapper
import org.junit.jupiter.api.Test
import javax.inject.Inject

open class CartViewRepositoryTest : ContextStarterTest() {

    @Inject private lateinit var repository: CartViewRepository
    @Inject private lateinit var wrapper: TransactionalWrapper

    @Test
    open fun `it should save cart view`() {
        val view = CartViewStub.random()

        wrapper.wrap { repository.save(view) }

        assertSimilar(view, repository.search(view.id))
    }

}