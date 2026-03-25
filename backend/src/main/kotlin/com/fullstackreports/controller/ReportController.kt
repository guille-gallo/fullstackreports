package com.fullstackreports.controller

import com.fullstackreports.dto.CreateReportRequest
import com.fullstackreports.dto.ReportDTO
import com.fullstackreports.dto.UpdateReportRequest
import com.fullstackreports.dto.toDTO
import com.fullstackreports.model.ReportStatus
import com.fullstackreports.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Manufacturing car report operations")
class ReportController(private val reportService: ReportService) {

    @GetMapping
    @Operation(summary = "List all reports", description = "Returns a paginated list of reports, optionally filtered by status")
    fun getAll(
        @Parameter(description = "Filter by report status") @RequestParam status: ReportStatus?,
        pageable: Pageable
    ): ResponseEntity<Page<ReportDTO>> {
        val result = if (status != null) {
            val filtered = reportService.getByStatus(status)
            org.springframework.data.domain.PageImpl(filtered.map { it.toDTO() })
        } else {
            reportService.getAll(pageable).map { it.toDTO() }
        }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID", description = "Returns a single report by its ID")
    fun getById(@PathVariable id: String): ResponseEntity<ReportDTO> {
        val report = reportService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(report.toDTO())
    }

    @PostMapping
    @Operation(summary = "Create a new report", description = "Creates a new manufacturing car report")
    fun create(@RequestBody request: CreateReportRequest): ResponseEntity<ReportDTO> {
        val created = reportService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created.toDTO())
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a report", description = "Updates an existing report by ID")
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateReportRequest
    ): ResponseEntity<ReportDTO> {
        val updated = reportService.update(id, request) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updated.toDTO())
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a report", description = "Deletes a report by ID")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        return if (reportService.delete(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
