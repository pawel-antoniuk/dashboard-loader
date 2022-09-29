package pl.antoniuk.dashboardloader.model

data class DashboardReference(
    val environment: DashboardEnvironment = DashboardEnvironment(),
    val id: String = ""
) {
    companion object {
        val emptyDashboardReference = DashboardReference()
    }
    override fun toString() =
        "${environment.name}/${environment.marketName}/$id"
}