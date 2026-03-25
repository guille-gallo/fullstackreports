import { useQuery } from '@tanstack/react-query'
import { fetchReports, fetchReportById } from './reportService'

export function useReports() {
  return useQuery({
    queryKey: ['reports'],
    queryFn: fetchReports,
  })
}

export function useReport(id: string) {
  return useQuery({
    queryKey: ['reports', id],
    queryFn: () => fetchReportById(id),
    enabled: !!id,
  })
}
