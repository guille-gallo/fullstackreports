package com.fullstackreports.repository

import com.fullstackreports.model.Report
import com.fullstackreports.model.ReportStatus
import org.springframework.data.mongodb.repository.MongoRepository

interface ReportRepository : MongoRepository<Report, String> {
    fun findByVin(vin: String): List<Report>
    fun findByStatus(status: ReportStatus): List<Report>
}
