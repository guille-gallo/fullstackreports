package com.fullstackreports.dto

import com.fullstackreports.model.Report
import com.fullstackreports.model.ReportStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Report response")
data class ReportDTO(
    @Schema(description = "Report ID", example = "65f1a2b3c4d5e6f7a8b9c0d1")
    val id: String,
    @Schema(description = "Vehicle Identification Number", example = "1HGCM82633A004352")
    val vin: String,
    @Schema(description = "Car model", example = "Civic")
    val model: String,
    @Schema(description = "Manufacturing year", example = "2024")
    val year: Int,
    @Schema(description = "Inspector name", example = "John Smith")
    val inspector: String,
    @Schema(description = "Inspection date", example = "2024-03-15")
    val date: LocalDate,
    @Schema(description = "Report status", example = "PENDING")
    val status: ReportStatus,
    @Schema(description = "Additional notes", example = "Minor paint defect on rear door")
    val notes: String?
)

@Schema(description = "Create report request")
data class CreateReportRequest(
    @Schema(description = "Vehicle Identification Number", required = true, example = "1HGCM82633A004352")
    val vin: String,
    @Schema(description = "Car model", required = true, example = "Civic")
    val model: String,
    @Schema(description = "Manufacturing year", required = true, example = "2024")
    val year: Int,
    @Schema(description = "Inspector name", required = true, example = "John Smith")
    val inspector: String,
    @Schema(description = "Inspection date", required = true, example = "2024-03-15")
    val date: LocalDate,
    @Schema(description = "Report status", required = true, example = "PENDING")
    val status: ReportStatus,
    @Schema(description = "Additional notes", example = "Minor paint defect on rear door")
    val notes: String? = null
)

@Schema(description = "Update report request")
data class UpdateReportRequest(
    @Schema(description = "Vehicle Identification Number", example = "1HGCM82633A004352")
    val vin: String? = null,
    @Schema(description = "Car model", example = "Civic")
    val model: String? = null,
    @Schema(description = "Manufacturing year", example = "2024")
    val year: Int? = null,
    @Schema(description = "Inspector name", example = "John Smith")
    val inspector: String? = null,
    @Schema(description = "Inspection date", example = "2024-03-15")
    val date: LocalDate? = null,
    @Schema(description = "Report status", example = "APPROVED")
    val status: ReportStatus? = null,
    @Schema(description = "Additional notes", example = "Resolved — repainted")
    val notes: String? = null
)

fun Report.toDTO() = ReportDTO(
    id = id!!,
    vin = vin,
    model = model,
    year = year,
    inspector = inspector,
    date = date,
    status = status,
    notes = notes
)

fun CreateReportRequest.toEntity() = Report(
    vin = vin,
    model = model,
    year = year,
    inspector = inspector,
    date = date,
    status = status,
    notes = notes
)
