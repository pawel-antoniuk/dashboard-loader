package pl.antoniuk.dashboardloader.service.dashboardConversion

class RuleSubstitutor(
    rule: RegexReplaceRule,
    params: Map<String, String>
) {
    private val convertedParams = when (rule.replaceCaseType) {
        RegexReplaceRule.ReplaceCaseType.KEEP_ORIGINAL_CASE -> params
        RegexReplaceRule.ReplaceCaseType.ARGS_TO_UPPERCASE -> params.entries
        .associate { (name, value) -> name to value.uppercase() }
    }

    fun substitute(providedRuleTarget: String) =
        convertedParams.entries.fold(providedRuleTarget) { ruleTarget, (paramName, paramValue) ->
            Regex("<$paramName>").replace(ruleTarget, paramValue)
        }
}