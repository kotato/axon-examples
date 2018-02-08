package com.kotato.context.ecommerce.infrastructure

import com.kotato.EcommerceApplication
import com.kotato.cqrs.domain.command.Command
import com.kotato.cqrs.domain.command.CommandBus
import com.kotato.cqrs.domain.command.CommandHandler
import org.axonframework.commandhandling.NoHandlerForCommandException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.inject.Inject
import javax.inject.Named
import kotlin.test.assertFailsWith

@SpringBootTest(classes = arrayOf(EcommerceApplication::class))
@ExtendWith(SpringExtension::class)
class CqrsCommandBusTest {
    @Inject
    private lateinit var commandBus: CommandBus

    @Test
    fun `it should handle a command`() {
        commandBus.handle(TestCommand())
    }

    @Test
    fun `it should fail because command handler does not exist`() {
        assertFailsWith(NoHandlerForCommandException::class) {
            commandBus.handle(TestWithoutHandlerCommand())
        }
    }

    class TestCommand : Command
    class TestWithoutHandlerCommand : Command

    @Named
    open class TestCommandHandler {
        @CommandHandler
        fun on(command: TestCommand) {
        }
    }
}