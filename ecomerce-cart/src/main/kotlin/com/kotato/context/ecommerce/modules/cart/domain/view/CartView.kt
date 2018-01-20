package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
@DynamicUpdate
data class CartView(@EmbeddedId val id: CartId,
                    val createdOn: ZonedDateTime,
                    @Embedded val userId: UserId,
                    val cartItems: CartItems = emptyMap()) : Serializable