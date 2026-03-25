package com.fullstackreports.config

import com.fullstackreports.model.Report
import com.fullstackreports.model.ReportStatus
import com.fullstackreports.repository.ReportRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataInitializer(private val reportRepository: ReportRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (reportRepository.count() > 0) return

        val reports = listOf(
            Report(
                vin = "1HGCM82633A004352",
                model = "Civic",
                year = 2024,
                inspector = "John Smith",
                date = LocalDate.of(2024, 3, 15),
                status = ReportStatus.APPROVED,
                notes = "All checks passed. Vehicle meets quality standards."
            ),
            Report(
                vin = "2T1BURHE5FC123456",
                model = "Corolla",
                year = 2024,
                inspector = "Maria Garcia",
                date = LocalDate.of(2024, 3, 16),
                status = ReportStatus.PENDING,
                notes = "Awaiting paint inspection results."
            ),
            Report(
                vin = "3VWFE21C04M012345",
                model = "Jetta",
                year = 2023,
                inspector = "David Lee",
                date = LocalDate.of(2024, 3, 10),
                status = ReportStatus.REJECTED,
                notes = "Door alignment issue on driver side. Requires rework."
            ),
            Report(
                vin = "5YJSA1DN5DFP14555",
                model = "Model S",
                year = 2025,
                inspector = "Sarah Johnson",
                date = LocalDate.of(2024, 4, 1),
                status = ReportStatus.IN_PROGRESS,
                notes = "Electrical systems under review."
            ),
            Report(
                vin = "WBAJB0C51JB084567",
                model = "530i",
                year = 2024,
                inspector = "John Smith",
                date = LocalDate.of(2024, 3, 20),
                status = ReportStatus.APPROVED,
                notes = null
            ),
            Report(
                vin = "1G1YY22G965109876",
                model = "Corvette",
                year = 2024,
                inspector = "Maria Garcia",
                date = LocalDate.of(2024, 3, 18),
                status = ReportStatus.PENDING,
                notes = "Waiting for engine performance data."
            ),
            Report(
                vin = "JH4KA8260MC012345",
                model = "Legend",
                year = 2023,
                inspector = "David Lee",
                date = LocalDate.of(2024, 2, 28),
                status = ReportStatus.APPROVED,
                notes = "Final inspection complete. Ready for shipping."
            ),
            Report(
                vin = "WAUZZZ4G6BN012345",
                model = "A6",
                year = 2025,
                inspector = "Sarah Johnson",
                date = LocalDate.of(2024, 4, 5),
                status = ReportStatus.IN_PROGRESS,
                notes = "Body panel gaps being measured."
            ),
            Report(
                vin = "1FTFW1ET5EKF12345",
                model = "F-150",
                year = 2024,
                inspector = "Carlos Ruiz",
                date = LocalDate.of(2024, 3, 22),
                status = ReportStatus.REJECTED,
                notes = "Weld quality below threshold on frame joint B7."
            ),
            Report(
                vin = "KMHD35LH5EU123456",
                model = "Elantra",
                year = 2024,
                inspector = "Carlos Ruiz",
                date = LocalDate.of(2024, 3, 25),
                status = ReportStatus.PENDING,
                notes = "Scheduled for final QA review."
            )
        )

        reportRepository.saveAll(reports)
        println("✓ Seeded ${reports.size} sample reports")
    }
}
