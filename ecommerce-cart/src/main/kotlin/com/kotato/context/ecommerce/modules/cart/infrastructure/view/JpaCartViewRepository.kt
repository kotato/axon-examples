package com.kotato.context.ecommerce.modules.cart.infrastructure.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.view.CartView
import com.kotato.context.ecommerce.modules.cart.domain.view.CartViewRepository
import com.kotato.shared.transaction.ReadModelTransaction
import org.springframework.data.jpa.repository.JpaRepository
import javax.inject.Named

@Named
open class JpaCartViewRepository(private val persistenceRepository: JpaCartViewPersistenceRepository) : CartViewRepository {

    override fun save(entity: CartView) {
        entity.let(persistenceRepository::saveAndFlush)
    }

    override fun search(id: CartId): CartView? = id.let(persistenceRepository::findOne)

    interface JpaCartViewPersistenceRepository : JpaRepository<CartView, CartId>

}