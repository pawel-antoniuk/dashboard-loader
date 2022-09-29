package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.model.DashboardContent
import pl.antoniuk.dashboardloader.model.DashboardReference
import pl.antoniuk.dashboardloader.model.request.ExportObjectsRequest
import pl.antoniuk.dashboardloader.service.dashboardConversion.DashboardRestTemplateProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DashboardExporter(
    private val dashboardRestTemplateProvider: DashboardRestTemplateProvider,
) {

    companion object {
        const val URL_PATH_DASHBOARD_EXPORT = "/api/saved_objects/_export"
    }

    private val logger = LoggerFactory.getLogger(DashboardExporter::class.java)!!

    fun exportDashboard(dashboardReference: DashboardReference): DashboardContent {
        val restTemplate = dashboardRestTemplateProvider
            .getRestTemplate(dashboardReference.environment)

        val requestBody = ExportObjectsRequest(
            true,
            listOf(
                ExportObjectsRequest.ObjectToExport("dashboard", dashboardReference.id)
            )
        )

        val response = restTemplate.postForObject(URL_PATH_DASHBOARD_EXPORT, requestBody, String::class.java)

        logger.debug("[dashboard-exporter] exported dashboard $dashboardReference")

        return DashboardContent(response!!)
    }
}