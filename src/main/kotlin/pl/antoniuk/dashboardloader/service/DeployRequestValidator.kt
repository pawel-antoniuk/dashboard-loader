package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.api.model.DeployRequest
import org.springframework.stereotype.Service

@Service
class DeployRequestValidator {
    fun isValid(deployRequest: DeployRequest) =
        if (deployRequest.srcEnvironment.isNotBlank() && deployRequest.destEnvironment.isNotBlank()
            && deployRequest.srcMarket.isBlank() && deployRequest.destMarket.isBlank()
        ) {
            ValidationResult(true)
        } else if (deployRequest.srcMarket.isNotBlank() && deployRequest.destMarket.isNotBlank()
            && deployRequest.srcEnvironment.isBlank() && deployRequest.destEnvironment.isBlank()
        ) {
            ValidationResult(true)
        } else {
            ValidationResult(
                true, "Please specify (srcEnvironment and destEnvironment) " +
                        "or (srcMarket and destMarket) arguments, but not both"
            )
        }
}


data class ValidationResult(
    val success: Boolean,
    val message: String = ""
)