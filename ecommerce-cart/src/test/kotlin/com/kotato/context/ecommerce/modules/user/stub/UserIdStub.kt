package com.kotato.context.ecommerce.modules.user.stub

import com.kotato.context.ecommerce.modules.user.domain.UserId
import java.util.UUID

class UserIdStub {
    companion object {
        fun random() = UserId(UUID.randomUUID())
    }
}