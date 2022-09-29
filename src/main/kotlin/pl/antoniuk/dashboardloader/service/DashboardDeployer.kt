package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.model.DashboardEnvironment
import pl.antoniuk.dashboardloader.model.DashboardReference
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DashboardDeployer(
    private val dashboardExplorer: DashboardExplorer,
    private val dashboardCopier: DashboardCopier
) {
    private val logger = LoggerFactory.getLogger(DashboardDeployer::class.java)!!

    fun deployToEnvironment(srcEnvironmentName: String, destEnvironmentName: String): DeployResponse {
        logger.info("[dashboard-deployer] start, src: $srcEnvironmentName, dest: $destEnvironmentName")

        val deployedDashboards = getDashboardsForEnvironment(srcEnvironmentName)
            .mapNotNull { srcDashboard ->
                val destEnvironment = srcDashboard.environment.copy(name = destEnvironmentName)
                val destDashboardReference = srcDashboard.copy(environment = destEnvironment)
                if (dashboardCopier.copy(srcDashboard, destDashboardReference).success) {
                    srcDashboard
                } else {
                    null
                }
            }

        logger.info("[dashboard-deployer] end, deployed-dashboards: $deployedDashboards")

        return DeployResponse(deployedDashboards)
    }

    private fun getDashboardsForEnvironment(environmentName: String) =
        dashboardExplorer.getDashboardIdsForEnvironment(environmentName).flatMap { it.value }

}

data class DeployResponse(
    val deployedDashboards: List<DashboardReference> = emptyList()
)