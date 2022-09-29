package pl.antoniuk.dashboardloader.api

import pl.antoniuk.dashboardloader.api.model.DeployRequest
import pl.antoniuk.dashboardloader.service.DashboardDeployer
import pl.antoniuk.dashboardloader.service.DashboardExplorer
import pl.antoniuk.dashboardloader.service.DeployRequestValidator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
class DashboardController(
    private val explorer: DashboardExplorer,
    private val deployer: DashboardDeployer,
    private val deployRequestValidator: DeployRequestValidator
) {
    @GetMapping("/api/dashboard")
    fun getAllIds() =
        explorer.environmentDashboardMap

    @PostMapping("/api/dashboard/_deploy")
    fun deploy(@RequestBody deployRequest: DeployRequest) =
        deployRequestValidator.isValid(deployRequest).let {
            deployer.deployToEnvironment(deployRequest.srcEnvironment, deployRequest.destEnvironment)
        }

}