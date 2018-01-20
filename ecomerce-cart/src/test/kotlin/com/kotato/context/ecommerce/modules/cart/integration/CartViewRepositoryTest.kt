package com.kotato.context.ecommerce.modules.cart.integration

import com.kotato.assertSimilar.MatcherSimilar.assertSimilar
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.shared.ContextStarterTest
import org.junit.jupiter.api.Test
import javax.inject.Inject

open class CartViewRepositoryTest : ContextStarterTest() {

    @Inject private lateinit var repository: CartViewRepository

    @Test
    open fun `it should save cart view`() {
        val view = CartViewStub.random()

        repository.save(view)

        assertSimilar(view, repository.search(view.id))
    }

}