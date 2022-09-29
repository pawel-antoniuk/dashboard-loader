package pl.antoniuk.dashboardloader.model.response

data class ImportResult(
    val success: Boolean = false,
    val successCount: Int = 0
)