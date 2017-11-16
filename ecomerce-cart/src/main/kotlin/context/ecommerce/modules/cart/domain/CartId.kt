package context.ecommerce.modules.cart.domain

import shared.noarg.NoArgsConstructor
import java.io.Serializable
import java.util.UUID

@NoArgsConstructor
data class CartId(val id: UUID) : Serializable {

    override fun toString(): String {
        return this.id.toString()
    }

    fun asString(): String {
        return this.id.toString()
    }

    companion object {
        fun fromString(uuid: String): CartId {
            return CartId(UUID.fromString(uuid))
        }
    }

}