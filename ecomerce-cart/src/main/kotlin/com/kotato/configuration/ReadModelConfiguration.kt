package com.kotato.configuration

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.inject.Named
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = arrayOf("com.kotato.context"),
                       entityManagerFactoryRef = "readModelEntityManagerFactory",
                       considerNestedRepositories = true)
open class ReadModelConfiguration {

    @Bean(name = arrayOf("readModelDataSource"))
    open fun dataSource(
            @Value("\${read-model.datasource.url}") url: String,
            @Value("\${read-model.datasource.username}") username: String,
            @Value("\${read-model.datasource.password}") password: String,
            @Value("\${read-model.datasource.driver-class-name}") driver: String,
            @Value("\${read-model.hibernate.c3p0.max-size}") maxSize: Int,
            @Value("\${read-model.hibernate.c3p0.min-size}") minSize: Int,
            @Value("\${read-model.hibernate.c3p0.test-query}") testQuery: String,
            @Value("\${read-model.hibernate.c3p0.idle-test-period}") idleTestPeriod: Int): DataSource =
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

    @Bean(name = arrayOf("readModelEntityManagerFactory"))
    open fun readModelEntityManagerFactory(@Named("readModelDataSource") dataSource: DataSource,
                                           @Value("\${read-model.hibernate.ddl-auto}") ddlAuto: String,
                                           @Value("\${read-model.hibernate.show-sql}") showSql: Boolean): EntityManagerFactory =
            LocalContainerEntityManagerFactoryBean().also {
                it.dataSource = dataSource
                it.persistenceUnitName = "read_model"
                it.jpaVendorAdapter = HibernateJpaVendorAdapter().also {
                    it.setGenerateDdl(true)
                    it.setShowSql(showSql)
                }
                it.jpaPropertyMap = mapOf(AvailableSettings.IMPLICIT_NAMING_STRATEGY to ImplicitNamingStrategyComponentPathImpl::class.java.canonicalName,
                                          AvailableSettings.HBM2DDL_AUTO to ddlAuto)
                it.setPackagesToScan("com.kotato.context")
                it.afterPropertiesSet()
            }.`object`

    @Bean(name = arrayOf("readModelPlatformTransactionManager"))
    open fun readModelTransactionManager(
            @Named("readModelEntityManagerFactory") projectionEntityManagerFactory: EntityManagerFactory):
            PlatformTransactionManager = JpaTransactionManager(projectionEntityManagerFactory)
}
