package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.config.EnvironmentsConfiguration
import pl.antoniuk.dashboardloader.model.DashboardEnvironment
import pl.antoniuk.dashboardloader.model.DashboardReference
import pl.antoniuk.dashboardloader.model.response.SavedObjectsResponse
import pl.antoniuk.dashboardloader.service.dashboardConversion.DashboardRestTemplateProvider
import org.springframework.stereotype.Service

@Service
class DashboardExplorer(
    private val dashboardRestTemplateProvider: DashboardRestTemplateProvider,
    private val environmentsConfiguration: EnvironmentsConfiguration
) {
    companion object {
        const val URL_PATH_GET_SAVED_OBJECTS = "/api/saved_objects/_find?type=dashboard&per_page=10000"
    }

    val environmentDashboardMap =
        environmentsConfiguration.environments.entries.associate { (environmentName, environment) ->
            environmentName to environment.markets.entries.associate { (marketName, market) ->
                marketName to getDashboardIdsForEnvironment(DashboardEnvironment(environmentName, marketName))
            }
        }

    fun getDashboardIdsForEnvironment(dashboardEnvironment: DashboardEnvironment): List<String> {
        val restTemplate = dashboardRestTemplateProvider.getRestTemplate(dashboardEnvironment)
        val savedObjects = restTemplate.getForObject(
            URL_PATH_GET_SAVED_OBJECTS,
            SavedObjectsResponse::class.java
        )
        return savedObjects?.saved_objects?.map {
            it.id
        } ?: emptyList()
    }

    fun getDashboardIdsForEnvironment(environmentName: String) =
        environmentsConfiguration.environments
            .getValue(environmentName).markets.entries
            .associate { (marketName, market) ->
                val environment = DashboardEnvironment(environmentName, marketName)
                val restTemplate = dashboardRestTemplateProvider.getRestTemplate(environment)
                val savedObjects = restTemplate.getForObject(
                    URL_PATH_GET_SAVED_OBJECTS,
                    SavedObjectsResponse::class.java
                )?.saved_objects?.map {
                    DashboardReference(DashboardEnvironment(environmentName, marketName), it.id)
                } ?: emptyList()
                marketName to savedObjects
            }


}

