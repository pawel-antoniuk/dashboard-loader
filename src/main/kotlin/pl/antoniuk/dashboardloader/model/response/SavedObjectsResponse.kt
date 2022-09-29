package pl.antoniuk.dashboardloader.model.response

data class SavedObjectsResponse(
    val page: Int = 0,
    val perPage: Int = 0,
    val total: Int = 0,
    val saved_objects: List<SavedObjectResponse> = emptyList()
)