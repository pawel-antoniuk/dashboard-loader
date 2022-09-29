package pl.antoniuk.dashboardloader.service.dashboardConversion

import pl.antoniuk.dashboardloader.config.EnvironmentsConfiguration
import pl.antoniuk.dashboardloader.model.DashboardEnvironment
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.MediaType
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.stereotype.Service

@Service
class DashboardRestTemplateProvider(
    environmentsConfiguration: EnvironmentsConfiguration,
    restTemplateBuilder: RestTemplateBuilder,
    objectMapper: ObjectMapper
) {

    private val loaderRestTemplates = environmentsConfiguration.environments
        .mapValues { (_, environment) ->
            environment.markets.entries.associate { (marketName, market) ->
                marketName to restTemplateBuilder
                    .rootUri(market.url)
                    .basicAuthentication(market.username, market.password)
                    .defaultHeader("kbn-xsrf", "reporting")
                    .additionalMessageConverters(
                        StringHttpMessageConverter().apply {
                            supportedMediaTypes = listOf(MediaType.APPLICATION_NDJSON)
                        },
                        FormHttpMessageConverter().apply {
                            supportedMediaTypes = listOf(
                                MediaType.MULTIPART_FORM_DATA,
                                MediaType("multipart", "related"))
                        }
                    )
                    .build()!!
            }
        }

    fun getRestTemplate(dashboardEnvironment: DashboardEnvironment) =
        loaderRestTemplates.getValue(dashboardEnvironment.name).getValue(dashboardEnvironment.marketName)

}