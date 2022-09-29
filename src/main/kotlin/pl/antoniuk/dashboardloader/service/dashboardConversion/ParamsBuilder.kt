package pl.antoniuk.dashboardloader.service.dashboardConversion

import pl.antoniuk.dashboardloader.model.DashboardReference

class ParamsBuilder(
    private val srcDashboard: DashboardReference = DashboardReference.emptyDashboardReference,
    private val destDashboard: DashboardReference = DashboardReference.emptyDashboardReference
) {
    fun build() =
        mapOf(
            "src-market" to srcDashboard.environment.marketName,
            "src-environment" to srcDashboard.environment.name,
            "dest-market" to destDashboard.environment.marketName,
            "dest-environment" to destDashboard.environment.name
        )
}