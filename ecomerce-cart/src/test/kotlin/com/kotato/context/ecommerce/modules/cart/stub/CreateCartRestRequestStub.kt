package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.adapter.create.CreateCartRestRequest
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import java.util.UUID

class CreateCartRestRequestStub {
    companion object {
        fun random(cartId: UUID = CartIdStub.random().id,
                   userId: UUID = UserIdStub.random().id) = CreateCartRestRequest(cartId, userId)
    }
}