package com.fullstackreports.service

import com.fullstackreports.dto.CreateReportRequest
import com.fullstackreports.dto.UpdateReportRequest
import com.fullstackreports.dto.toEntity
import com.fullstackreports.model.Report
import com.fullstackreports.model.ReportStatus
import com.fullstackreports.repository.ReportRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReportService(private val reportRepository: ReportRepository) {

    fun getAll(pageable: Pageable): Page<Report> =
        reportRepository.findAll(pageable)

    fun getByStatus(status: ReportStatus): List<Report> =
        reportRepository.findByStatus(status)

    fun getById(id: String): Report? =
        reportRepository.findById(id).orElse(null)

    fun create(request: CreateReportRequest): Report =
        reportRepository.save(request.toEntity())

    fun update(id: String, request: UpdateReportRequest): Report? {
        val existing = reportRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(
            vin = request.vin ?: existing.vin,
            model = request.model ?: existing.model,
            year = request.year ?: existing.year,
            inspector = request.inspector ?: existing.inspector,
            date = request.date ?: existing.date,
            status = request.status ?: existing.status,
            notes = request.notes ?: existing.notes
        )
        return reportRepository.save(updated)
    }

    fun delete(id: String): Boolean {
        if (!reportRepository.existsById(id)) return false
        reportRepository.deleteById(id)
        return true
    }
}
