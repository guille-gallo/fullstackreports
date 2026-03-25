export interface ReportDTO {
  id: string
  vin: string
  model: string
  year: number
  inspector: string
  date: string
  status: 'PENDING' | 'IN_PROGRESS' | 'APPROVED' | 'REJECTED'
  notes: string | null
}

export interface PageResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

const BASE_URL = '/api'

export async function fetchReports(): Promise<ReportDTO[]> {
  const res = await fetch(`${BASE_URL}/reports?size=100`)
  if (!res.ok) throw new Error(`Failed to fetch reports: ${res.status}`)
  const page: PageResponse<ReportDTO> = await res.json()
  return page.content
}

export async function fetchReportById(id: string): Promise<ReportDTO> {
  const res = await fetch(`${BASE_URL}/reports/${encodeURIComponent(id)}`)
  if (!res.ok) throw new Error(`Failed to fetch report: ${res.status}`)
  return res.json()
}
