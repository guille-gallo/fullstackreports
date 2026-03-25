package com.fullstackreports.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

enum class ReportStatus {
    PENDING,
    IN_PROGRESS,
    APPROVED,
    REJECTED
}

@Document(collection = "reports")
data class Report(
    @Id val id: String? = null,
    val vin: String,
    val model: String,
    val year: Int,
    val inspector: String,
    val date: LocalDate,
    val status: ReportStatus,
    val notes: String? = null
)
