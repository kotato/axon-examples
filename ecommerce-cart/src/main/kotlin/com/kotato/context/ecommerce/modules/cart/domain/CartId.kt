package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column

@ValueObject
data class CartId(@Column(columnDefinition = "binary(16)") val id: UUID) : Serializable {

    override fun toString() = this.id.toString()

    fun asString() = this.id.toString()

    companion object {
        fun fromString(uuid: String) = CartId(UUID.fromString(uuid))
    }

}