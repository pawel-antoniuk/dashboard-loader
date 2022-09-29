package pl.antoniuk.dashboardloader.api.model

data class DeployRequest(
    val srcEnvironment: String = "",
    val destEnvironment: String = "",
    val srcMarket: String = "",
    val destMarket: String = ""
)
