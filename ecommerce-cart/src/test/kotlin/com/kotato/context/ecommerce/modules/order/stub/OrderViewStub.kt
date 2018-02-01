package com.kotato.context.ecommerce.modules.order.stub

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.cart.stub.CartIdStub
import com.kotato.context.ecommerce.modules.cart.stub.CartItemsStub
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.stub.PaymentIdStub
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.context.ecommerce.modules.user.stub.UserIdStub
import com.kotato.shared.money.Money
import com.kotato.shared.stub.MoneyStub
import java.time.ZonedDateTime

class OrderViewStub {
    companion object {
        fun random(id: OrderId = OrderIdStub.random(),
                   occurredOn: ZonedDateTime = ZonedDateTime.now(),
                   userId: UserId = UserIdStub.random(),
                   cartId: CartId = CartIdStub.random(),
                   paymentId: PaymentId = PaymentIdStub.random(),
                   price: Money = MoneyStub.random(),
                   status: OrderStatus = OrderStatusStub.random(),
                   cartItems: CartItems = CartItemsStub.random())
                = OrderView(id, occurredOn, userId, cartId, paymentId, price, status, cartItems)
    }
}