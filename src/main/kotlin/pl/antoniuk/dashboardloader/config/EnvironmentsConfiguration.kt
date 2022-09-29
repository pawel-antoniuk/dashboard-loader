package pl.antoniuk.dashboardloader.config

import org.springframework.boot.context.properties.ConfigurationProperties

class Market {
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
}

class Environment{
    lateinit var markets: Map<String, Market>
}


@ConfigurationProperties(prefix = "loader")
class EnvironmentsConfiguration {
    lateinit var environments: Map<String, Environment>
}
