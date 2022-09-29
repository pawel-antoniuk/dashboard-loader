package pl.antoniuk.dashboardloader.service.dashboardConversion

import pl.antoniuk.dashboardloader.config.BackupConfiguration
import pl.antoniuk.dashboardloader.model.DashboardContent
import pl.antoniuk.dashboardloader.model.DashboardReference
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.outputStream

@Service
class DashboardBackupService(
    private val backupConfiguration: BackupConfiguration
) {

    private val logger = LoggerFactory.getLogger(DashboardBackupService::class.java)!!

    fun saveBackup(dashboardReference: DashboardReference, dashboardContent: DashboardContent) {
        val dirPath = Path(
            backupConfiguration.path,
            dashboardReference.environment.name,
            dashboardReference.environment.marketName
        )

        val filename = "orbico-" +
                "${dashboardReference.environment.name}-" +
                "${dashboardReference.environment.marketName}-" +
                "${dashboardReference.id}-" +
                "${dashboardReference.id}-" +
                SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(Date()) +
                ".ndjson"

        dirPath.createDirectories()

        val absoluteFilenamePath = dirPath.resolve(filename)
        absoluteFilenamePath.outputStream().writer().use {
            it.write(dashboardContent.content)
        }

        logger.info("[dashboard-backup] dashboard $dashboardReference saved to file $absoluteFilenamePath")
    }
}