package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.model.DashboardContent
import pl.antoniuk.dashboardloader.model.DashboardReference
import pl.antoniuk.dashboardloader.model.response.ImportResult
import pl.antoniuk.dashboardloader.service.dashboardConversion.DashboardRestTemplateProvider
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@Service
class DashboardImporter(
    private val dashboardRestTemplateProvider: DashboardRestTemplateProvider
) {

    companion object {
        const val URL_PATH_DASHBOARD_IMPORT = "/api/saved_objects/_import?overwrite=true"
    }

    private val logger = LoggerFactory.getLogger(DashboardImporter::class.java)!!

    fun importDashboard(dashboardReference: DashboardReference, dashboardContent: DashboardContent): ImportResult {
        val restTemplate = dashboardRestTemplateProvider
            .getRestTemplate(dashboardReference.environment)

        val httpEntity = prepareImportHttpEntity(dashboardContent)
        val response = restTemplate.postForEntity(URL_PATH_DASHBOARD_IMPORT, httpEntity, ImportResult::class.java)

        logger.debug("[dashboard-importer] imported dashboard $dashboardReference, response ${response.body}")

        return response.body ?: ImportResult()
    }

    private fun prepareImportHttpEntity(dashboardContent: DashboardContent): HttpEntity<MultiValueMap<String, Any>> {
        val contentDisposition = ContentDisposition
            .builder("form-data")
            .name("file")
            .filename("import.ndjson")
            .build()

        val fileMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())

        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("file", HttpEntity(dashboardContent.content.toByteArray(), fileMap))

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        return HttpEntity(body, headers)
    }

}