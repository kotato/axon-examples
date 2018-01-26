package com.kotato.context.ecommerce.modules.order.behaviour.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.order.behaviour.OrderId
import com.kotato.context.ecommerce.modules.order.behaviour.OrderStatus
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType

@Entity
@DynamicUpdate
data class OrderView(@EmbeddedId val id: OrderId,
                     val createdOn: ZonedDateTime,
                     @Embedded val userId: UserId,
                     @Embedded val cartId: CartId,
                     @Embedded val paymentId: PaymentId,
                     @Enumerated(EnumType.STRING) val status: OrderStatus,
                     @ElementCollection(fetch = FetchType.EAGER)
                     @AttributeOverride(name = "key.itemId.id",
                                        column = Column(columnDefinition = "binary(16)"))
                     val cartItems: CartItems) : Serializable