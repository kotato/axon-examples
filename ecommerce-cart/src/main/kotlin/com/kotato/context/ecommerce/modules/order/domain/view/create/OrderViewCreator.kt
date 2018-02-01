package com.kotato.context.ecommerce.modules.order.domain.view.create

import com.kotato.context.ecommerce.modules.cart.domain.CartId
import com.kotato.context.ecommerce.modules.cart.domain.CartItems
import com.kotato.context.ecommerce.modules.cart.domain.toCartItems
import com.kotato.context.ecommerce.modules.cart.domain.view.find.FindCartQueryAsker
import com.kotato.context.ecommerce.modules.order.domain.OrderId
import com.kotato.context.ecommerce.modules.order.domain.OrderStatus
import com.kotato.context.ecommerce.modules.order.domain.view.OrderView
import com.kotato.context.ecommerce.modules.order.domain.view.OrderViewRepository
import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.user.domain.UserId
import com.kotato.shared.money.Money
import com.kotato.shared.transaction.ReadModelTransaction
import java.time.ZonedDateTime
import javax.inject.Named

@Named
open class OrderViewCreator(private val repository: OrderViewRepository,
                            private val asker: FindCartQueryAsker) {

    @ReadModelTransaction
    open operator fun invoke(orderId: OrderId,
                             createdOn: ZonedDateTime,
                             cartId: CartId,
                             paymentId: PaymentId,
                             userId: UserId,
                             price: Money) {
        repository.save(OrderView(orderId,
                                  createdOn,
                                  userId,
                                  cartId,
                                  paymentId,
                                  price,
                                  OrderStatus.IN_PROGRESS,
                                  asker.ask(cartId).cartItems.toCartItems()))
    }

}