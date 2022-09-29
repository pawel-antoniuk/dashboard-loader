package pl.antoniuk.dashboardloader.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "backup")
class BackupConfiguration {
    lateinit var path: String
}