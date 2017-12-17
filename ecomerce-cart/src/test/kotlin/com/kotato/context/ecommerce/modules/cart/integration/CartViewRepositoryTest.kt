package com.kotato.context.ecommerce.modules.cart.integration

import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.context.ecommerce.modules.cart.stub.CartViewStub
import com.kotato.shared.ContextStarterTest
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.test.assertEquals

open class CartViewRepositoryTest : ContextStarterTest() {

    @Inject private lateinit var repository: CartViewRepository

    @Test
    open fun `it should save cart view`() {
        val view = CartViewStub.random()

        repository.save(view)

        assertEquals(view, repository.search(view.id))
    }

}

fun ZonedDateTime.equals(other: Any?): Boolean {
    return true
}