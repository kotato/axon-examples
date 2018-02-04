package com.kotato.context.ecommerce.modules.order.adapter.find

import com.kotato.context.ecommerce.modules.order.domain.view.OrderResponse
import com.kotato.context.ecommerce.modules.order.domain.view.find.by_id.FindOrderQuery
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.ask
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
open class FindOrderController(@Inject val queryBus: QueryBus) {

    @GetMapping("/ecommerce/order/{orderId}")
    open fun find(@PathVariable("orderId") id: String) =
            queryBus.ask<OrderResponse>(FindOrderQuery(id))
                    .toRestResponse()
                    .let { ResponseEntity.ok(it) }

}