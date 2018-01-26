package com.kotato.context.ecommerce.modules.cart.domain.view

import com.kotato.context.ecommerce.modules.cart.domain.toSerializedCartItems

fun CartView.toResponse() =
        CartResponse(this.id.asString(),
                     this.createdOn,
                     this.userId.asString(),
                     this.cartItems.toSerializedCartItems(),
                     this.checkout)