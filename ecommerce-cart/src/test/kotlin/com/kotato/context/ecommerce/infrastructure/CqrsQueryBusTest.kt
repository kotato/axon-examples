package com.kotato.context.ecommerce.infrastructure

import com.kotato.EcommerceApplication
import com.kotato.cqrs.domain.query.Query
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.domain.query.QueryHandler
import com.kotato.cqrs.domain.query.ask
import org.axonframework.queryhandling.NoHandlerForQueryException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.inject.Inject
import javax.inject.Named
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@SpringBootTest(classes = arrayOf(EcommerceApplication::class))
@ExtendWith(SpringExtension::class)
class CqrsQueryBusTest {
    @Inject
    private lateinit var queryBus: QueryBus

    @Test
    fun `it should handle a query`() {
        assertTrue(queryBus.ask(TestQuery()))
    }

    @Test
    fun `it should fail because query handler does not exist`() {
        assertFailsWith(NoHandlerForQueryException::class) {
            queryBus.ask(TestWithoutHandlerQuery())
        }
    }

    class TestQuery : Query
    class TestWithoutHandlerQuery : Query

    @Named
    open class TestQueryHandler {
        @QueryHandler
        fun on(query: TestQuery): Boolean {
            return true
        }
    }
}