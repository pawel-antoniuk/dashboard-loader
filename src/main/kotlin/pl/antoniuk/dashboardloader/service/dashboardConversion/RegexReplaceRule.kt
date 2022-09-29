package pl.antoniuk.dashboardloader.service.dashboardConversion

data class RegexReplaceRule(
    val find: String,
    val replace: String,
    val replaceCaseType: ReplaceCaseType = ReplaceCaseType.KEEP_ORIGINAL_CASE,
    val required: Boolean = false
) {
    enum class ReplaceCaseType {
        KEEP_ORIGINAL_CASE,
        ARGS_TO_UPPERCASE
    }
}

