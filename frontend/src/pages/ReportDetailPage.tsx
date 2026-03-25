import { useParams, useNavigate } from 'react-router-dom'
import { useReport } from '@/api/hooks'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import type { ReportDTO } from '@/api/reportService'

const statusVariant: Record<ReportDTO['status'], 'default' | 'secondary' | 'destructive' | 'outline'> = {
  APPROVED: 'default',
  PENDING: 'secondary',
  IN_PROGRESS: 'outline',
  REJECTED: 'destructive',
}

export default function ReportDetailPage() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const { data: report, isLoading, error } = useReport(id!)

  if (isLoading) {
    return <div className="text-muted-foreground">Loading report...</div>
  }

  if (error || !report) {
    return <div className="text-destructive">Error loading report: {error?.message ?? 'Not found'}</div>
  }

  return (
    <div className="max-w-2xl">
      <Button variant="outline" className="mb-4" onClick={() => navigate('/')}>
        ← Back to Reports
      </Button>

      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="text-2xl">Report: {report.vin}</CardTitle>
            <Badge variant={statusVariant[report.status]}>
              {report.status.replace('_', ' ')}
            </Badge>
          </div>
        </CardHeader>
        <CardContent className="space-y-4">
          <Field label="VIN" value={report.vin} />
          <Field label="Model" value={report.model} />
          <Field label="Year" value={String(report.year)} />
          <Field label="Inspector" value={report.inspector} />
          <Field label="Date" value={report.date} />
          <Field label="Notes" value={report.notes ?? 'No notes'} />
        </CardContent>
      </Card>
    </div>
  )
}

function Field({ label, value }: { label: string; value: string }) {
  return (
    <div>
      <dt className="text-sm font-medium text-muted-foreground">{label}</dt>
      <dd className="text-base mt-0.5">{value}</dd>
    </div>
  )
}
