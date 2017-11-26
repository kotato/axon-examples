package context.ecommerce.modules.cart.stub

import context.ecommerce.modules.cart.domain.create.CreateCartCommand
import context.ecommerce.modules.user.stub.UserIdStub

class CreateCartCommandStub {
    companion object {
        fun random(id: String = CartIdStub.random().asString(),
                   userId: String = UserIdStub.random().asString()): CreateCartCommand {
            return CreateCartCommand(id, userId)
        }
    }
}