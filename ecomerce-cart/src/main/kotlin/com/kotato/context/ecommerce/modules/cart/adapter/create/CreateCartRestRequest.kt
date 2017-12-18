package com.kotato.context.ecommerce.modules.cart.adapter.create

import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateCartRestRequest(
        @field:NotNull val id: UUID?,
        @field:NotNull val userId: UUID?
                                )