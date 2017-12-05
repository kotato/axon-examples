package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.Entity

@Entity
@DynamicUpdate
data class CartView(val id: CartId,
                    val createdOn: ZonedDateTime,
                    val userId: UserId) : Serializable