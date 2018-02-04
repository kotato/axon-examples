package com.kotato.context.ecommerce.modules.user.domain

import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column

@ValueObject
data class UserId(@Column(columnDefinition = "binary(16)") val id: UUID) : Serializable {

    override fun toString(): String {
        return id.toString()
    }

    fun asString(): String {
        return id.toString()
    }

    companion object {
        fun fromString(uuid: String): UserId {
            return UserId(UUID.fromString(uuid))
        }
    }

}