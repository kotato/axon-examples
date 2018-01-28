package com.kotato.context.ecommerce.modules.payment.domain.view

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.PaymentStatus
import com.kotato.shared.money.Money
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
@DynamicUpdate
data class PaymentView(@EmbeddedId val id: PaymentId,
                       val createdOn: ZonedDateTime,
                       @Embedded val price: Money,
                       @Embedded val status: PaymentStatus) : Serializable