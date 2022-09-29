package pl.antoniuk.dashboardloader

import pl.antoniuk.dashboardloader.config.BackupConfiguration
import pl.antoniuk.dashboardloader.config.EnvironmentsConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    EnvironmentsConfiguration::class,
    BackupConfiguration::class
)
class DashboardLoaderApplication

fun main(args: Array<String>) {
    runApplication<DashboardLoaderApplication>(*args)
}
