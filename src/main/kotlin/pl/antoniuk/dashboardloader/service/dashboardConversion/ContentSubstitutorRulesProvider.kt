package pl.antoniuk.dashboardloader.service.dashboardConversion

import org.springframework.stereotype.Service

@Service
class ContentSubstitutorRulesProvider {

    companion object {
        private val regexes = mapOf(
            RegexType.NAME_CONVERTER to listOf(
                RegexReplaceRule(
                    "orbico-<src-market>-events-<src-environment>-index-pattern-id",
                    "orbico-<dest-market>-events-<dest-environment>-index-pattern-id",
                    required = true
                ),
                RegexReplaceRule(
                    "orbico-<src-market>-events-<src-environment>",
                    "orbico-<dest-market>-events-<dest-environment>",
                    required = true
                ),
                RegexReplaceRule(
                    "<src-environment>",
                    "<dest-environment>",
                    RegexReplaceRule.ReplaceCaseType.ARGS_TO_UPPERCASE
                )
            )
        )
    }

    fun getRegexes(regexType: RegexType) = regexes.getValue(RegexType.NAME_CONVERTER)
}

enum class RegexType {
    NAME_CONVERTER
}