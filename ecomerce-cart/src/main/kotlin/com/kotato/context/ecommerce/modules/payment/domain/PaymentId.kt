package com.kotato.context.ecommerce.modules.payment.domain

import com.kotato.shared.valueobject.ValueObject
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column

@ValueObject
data class PaymentId(@Column(columnDefinition = "binary(16)") val id: UUID) : Serializable {

    override fun toString() = this.id.toString()

    fun asString() = this.id.toString()

    companion object {
        fun fromString(uuid: String) = PaymentId(UUID.fromString(uuid))
    }

}