package com.kotato.context.ecommerce.modules.cart.adapter.update

import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotNull

data class CartItemRestRequest(
        @field:NotNull val itemId: UUID?,
        @field:NotNull val price: BigDecimal?,
        @field:NotNull val currency: String?,
        @field:NotNull val quantity: Int?
                                      )