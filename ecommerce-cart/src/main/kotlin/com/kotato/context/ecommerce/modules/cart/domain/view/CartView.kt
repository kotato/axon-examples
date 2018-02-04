package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
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
import javax.persistence.FetchType

@Entity
@DynamicUpdate
data class CartView(@EmbeddedId val id: CartId,
                    val createdOn: ZonedDateTime,
                    @Embedded val userId: UserId,
                    @ElementCollection(fetch = FetchType.EAGER)
                    @AttributeOverride(name = "key.itemId.id",
                                       column = Column(columnDefinition = "binary(16)"))
                    val cartItems: CartItems = emptyMap(),
                    val checkout: Boolean = false) : Serializable