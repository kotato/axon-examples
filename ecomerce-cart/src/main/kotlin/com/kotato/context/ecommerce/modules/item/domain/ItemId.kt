package com.kotato.context.ecommerce.modules.item.domain

import com.kotato.shared.noarg.NoArgsConstructor
import java.io.Serializable
import java.util.UUID

@NoArgsConstructor
data class ItemId(val id: UUID) : Serializable {

    override fun toString(): String {
        return this.id.toString()
    }

    fun asString(): String {
        return this.id.toString()
    }

    companion object {
        fun fromString(uuid: String): ItemId {
            return ItemId(UUID.fromString(uuid))
        }
    }

}