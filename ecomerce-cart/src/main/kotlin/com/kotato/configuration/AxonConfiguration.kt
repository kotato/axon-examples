package com.kotato.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.axonframework.common.caching.Cache
import org.axonframework.common.caching.NoCache
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider
import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.saga.repository.jpa.JpaSagaStore
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
open class AxonConfiguration {
    @Bean
    @Primary
    open fun axonDataSource(
            @Value("\${write-model.datasource.url}") url: String,
            @Value("\${write-model.datasource.username}") username: String,
            @Value("\${write-model.datasource.password}") password: String,
            @Value("\${write-model.datasource.driver-class-name}") driver: String,
            @Value("\${write-model.hibernate.c3p0.max-size}") maxSize: Int,
            @Value("\${write-model.hibernate.c3p0.min-size}") minSize: Int,
            @Value("\${write-model.hibernate.c3p0.test-query}") testQuery: String,
            @Value("\${write-model.hibernate.c3p0.idle-test-period}") idleTestPeriod: Int): DataSource =
            ComboPooledDataSource().also {
                it.initialPoolSize = minSize
                it.minPoolSize = minSize
                it.maxPoolSize = maxSize
                it.idleConnectionTestPeriod = idleTestPeriod
                it.preferredTestQuery = testQuery
                it.jdbcUrl = url
                it.password = password
                it.user = username
                it.driverClass = driver
            }

    @Bean
    @Primary
    open fun entityManagerFactory(dataSource: DataSource,
                                  @Value("\${write-model.hibernate.ddl-auto}") ddlAuto: String,
                                  @Value("\${write-model.hibernate.show-sql}") showSql: Boolean): EntityManagerFactory =
            LocalContainerEntityManagerFactoryBean().also {
                it.dataSource = dataSource
                it.jpaVendorAdapter = HibernateJpaVendorAdapter().also {
                    it.setGenerateDdl(true)
                    it.setShowSql(showSql)
                }
                it.jpaPropertyMap = mapOf(AvailableSettings.IMPLICIT_NAMING_STRATEGY to ImplicitNamingStrategyComponentPathImpl::class.java.canonicalName,
                                          AvailableSettings.HBM2DDL_AUTO to ddlAuto)
                it.setPackagesToScan("org.axonframework")
                it.afterPropertiesSet()
            }.`object`

    @Bean
    @Primary
    open fun transactionManager(projectionEntityManagerFactory: EntityManagerFactory): PlatformTransactionManager =
            JpaTransactionManager(projectionEntityManagerFactory)

    @Bean
    @Primary
    open fun eventStorageEngine(serializer: Serializer,
                                dataSource: DataSource,
                                entityManagerProvider: EntityManagerProvider,
                                transactionManager: TransactionManager): EventStorageEngine =
            JpaEventStorageEngine(serializer, null, dataSource, entityManagerProvider, transactionManager)

    @Bean
    @Primary
    open fun sagaStore(serializer: Serializer,
                       entityManagerProvider: EntityManagerProvider): JpaSagaStore =
            JpaSagaStore(serializer, entityManagerProvider)

    @Bean
    @Primary
    open fun entityManagerProvider(): EntityManagerProvider = ContainerManagedEntityManagerProvider()

    @Bean
    @Primary
    open fun tokenStore(serializer: Serializer, entityManagerProvider: EntityManagerProvider): TokenStore =
            JpaTokenStore(entityManagerProvider, serializer)

    @Bean
    open fun springAggregateSnapshotterFactoryBean(): SpringAggregateSnapshotterFactoryBean =
            SpringAggregateSnapshotterFactoryBean()

    @Bean
    open fun snapshotter(factory: SpringAggregateSnapshotterFactoryBean,
                         platformTransactionManager: PlatformTransactionManager,
                         eventStore: EventStore): SpringAggregateSnapshotter =
            factory.also {
                it.setTransactionManager(platformTransactionManager)
                it.setEventStore(eventStore)
            }.`object`

    @Bean
    open fun serializer(): Serializer = JacksonSerializer(ObjectMapper().registerKotlinModule())

    @Bean
    open fun eventBus(eventStorageEngine: EventStorageEngine): EventBus = EmbeddedEventStore(eventStorageEngine)

    @Bean
    open fun cache(): Cache = NoCache.INSTANCE

    @Bean
    open fun eventStore(eventBus: EventBus): EventStore = eventBus as EventStore
}
