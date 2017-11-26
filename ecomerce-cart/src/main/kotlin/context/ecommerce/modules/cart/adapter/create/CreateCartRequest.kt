package context.ecommerce.modules.cart.adapter.create

import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateCartRequest(
        @field:NotNull val id: UUID?,
        @field:NotNull val userId: UUID?
                            )