package pl.antoniuk.dashboardloader.service.dashboardConversion

import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service
class ContentSubstitutor {
    fun substitute(
        providedInput: String,
        rules: List<RegexReplaceRule>,
        params: Map<String, String>
    ) =
        rules.fold(ReplacementResult(providedInput)) { currentResult, rule ->
            val ruleSubstitutor = RuleSubstitutor(rule, params)
            val find = ruleSubstitutor.substitute(rule.find)
            val replace = ruleSubstitutor.substitute(rule.replace)
            val newResult = replace(currentResult.content, find, replace)
            val newCount = currentResult.count + newResult.count
            val newMustMatchCount = currentResult.requiredCount + if(rule.required) newResult.count else 0

            ReplacementResult(newResult.content, newCount, newMustMatchCount)
        }

    private fun replace(inputContent: String, replaceFrom: String, replaceTo: String): InternalReplacementResult {
        val matcher: Matcher = Pattern.compile(replaceFrom).matcher(inputContent)
        var numOfReplacements = 0
        val stringBuffer = StringBuffer()

        while (matcher.find()) {
            numOfReplacements++
            matcher.appendReplacement(stringBuffer, replaceTo)
        }
        matcher.appendTail(stringBuffer)

        return InternalReplacementResult(stringBuffer.toString(), numOfReplacements)
    }

    data class ReplacementResult(
        val content: String,
        val count: Int = 0,
        val requiredCount: Int = 0
    )

    data class InternalReplacementResult(
        val content: String,
        val count: Int = 0
    )
}

