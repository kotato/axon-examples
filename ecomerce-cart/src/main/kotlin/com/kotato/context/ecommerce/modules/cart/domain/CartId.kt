package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column

@ValueObject
data class CartId(@Column(columnDefinition = "binary(16)") val id: UUID) : Serializable {

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