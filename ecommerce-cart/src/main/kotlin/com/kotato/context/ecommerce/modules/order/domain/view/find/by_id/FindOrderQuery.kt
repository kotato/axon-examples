package com.kotato.context.ecommerce.modules.order.domain.view.find.by_id

import com.kotato.cqrs.domain.query.Query

data class FindOrderQuery(val id: String) : Query