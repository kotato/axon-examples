package com.kotato.context.ecommerce.modules.cart.stub

import com.kotato.context.ecommerce.modules.cart.domain.create.CreateCartCommand
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub

class CreateCartCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString(),
                   userId: String = UserIdStub.random().asString()) = CreateCartCommand(id, userId)
    }
}