package pl.antoniuk.dashboardloader.service.dashboardConversion

import pl.antoniuk.dashboardloader.model.DashboardContent
import pl.antoniuk.dashboardloader.model.DashboardReference
import org.springframework.stereotype.Service

@Service
class DashboardConverter(
    private val contentSubstitutor: ContentSubstitutor,
    private val substitutorRulesProvider: ContentSubstitutorRulesProvider
) {
    fun convert(
        srcDashboard: DashboardReference,
        destDashboard: DashboardReference,
        inputDashboardContent: DashboardContent
    ): ConverterResult {
        val regexes = substitutorRulesProvider.getRegexes(RegexType.NAME_CONVERTER)
        val params = ParamsBuilder(srcDashboard, destDashboard).build()
        val replacementResult = contentSubstitutor.substitute(
            inputDashboardContent.content, regexes, params
        )

        return ConverterResult(
            replacementResult.requiredCount != 0,
            DashboardContent(replacementResult.content)
        )
    }
}

data class ConverterResult(
    val success: Boolean,
    val convertedContent: DashboardContent?
)