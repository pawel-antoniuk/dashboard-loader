package pl.antoniuk.dashboardloader.model.request

data class ExportObjectsRequest(
    val includeReferencesDeep: Boolean = false,
    val objects: List<ObjectToExport> = emptyList()
) {
    data class ObjectToExport(
        val type: String = "",
        val id: String = "",
    )
}