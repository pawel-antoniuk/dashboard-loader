package pl.antoniuk.dashboardloader.service

import pl.antoniuk.dashboardloader.model.DashboardReference
import pl.antoniuk.dashboardloader.model.response.ImportResult
import pl.antoniuk.dashboardloader.service.dashboardConversion.DashboardBackupService
import pl.antoniuk.dashboardloader.service.dashboardConversion.DashboardConverter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DashboardCopier(
    private val exporter: DashboardExporter,
    private val importer: DashboardImporter,
    private val converter: DashboardConverter,
    private val backupService: DashboardBackupService
) {
    private val logger = LoggerFactory.getLogger(DashboardCopier::class.java)!!

    fun copy(src: DashboardReference, dest: DashboardReference): CopyResult {
        val content = exporter.exportDashboard(src)
        val result = converter.convert(src, dest, content)

        return if (result.success && result.convertedContent != null) {
            backupService.saveBackup(dest, result.convertedContent)
            val importResult = importer.importDashboard(dest, result.convertedContent)
            logger.info("[dashboard-copier] copied from $src to $dest")
            CopyResult(true, importResult)
        } else {
            logger.info("[dashboard-copier] dashboard $src content cannot be matched to any rule (skipped)")
            CopyResult(false)
        }
    }
}

data class CopyResult(
    val success: Boolean = false,
    val importResult: ImportResult = ImportResult()
)