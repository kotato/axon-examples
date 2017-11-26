package context.ecommerce.modules.user.domain

import shared.noarg.NoArgsConstructor
import java.io.Serializable
import java.util.UUID

@NoArgsConstructor
data class UserId(val id: UUID) : Serializable {

    override fun toString(): String {
        return this.id.toString()
    }

    fun asString(): String {
        return this.id.toString()
    }

    companion object {
        fun fromString(uuid: String): UserId {
            return UserId(UUID.fromString(uuid))
        }
    }

}