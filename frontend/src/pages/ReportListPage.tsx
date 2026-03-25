import { useMemo } from 'react'
import { useNavigate } from 'react-router-dom'
import { AllCommunityModule, ModuleRegistry, type ColDef, type ICellRendererParams } from 'ag-grid-community'
import { AgGridReact } from 'ag-grid-react'
import { useReports } from '@/api/hooks'
import type { ReportDTO } from '@/api/reportService'
import { Badge } from '@/components/ui/badge'

ModuleRegistry.registerModules([AllCommunityModule])

const statusVariant: Record<ReportDTO['status'], 'default' | 'secondary' | 'destructive' | 'outline'> = {
  APPROVED: 'default',
  PENDING: 'secondary',
  IN_PROGRESS: 'outline',
  REJECTED: 'destructive',
}

function VinCellRenderer(params: ICellRendererParams<ReportDTO>) {
  const navigate = useNavigate()
  return (
    <button
      className="text-primary underline underline-offset-4 hover:text-primary/80 cursor-pointer"
      onClick={() => navigate(`/reports/${params.data?.id}`)}
    >
      {params.value}
    </button>
  )
}

function StatusCellRenderer(params: ICellRendererParams<ReportDTO>) {
  const status = params.value as ReportDTO['status']
  return <Badge variant={statusVariant[status]}>{status.replace('_', ' ')}</Badge>
}

export default function ReportListPage() {
  const { data: reports, isLoading, error } = useReports()

  const columnDefs = useMemo<ColDef<ReportDTO>[]>(() => [
    { field: 'vin', headerName: 'VIN', cellRenderer: VinCellRenderer, flex: 2 },
    { field: 'model', headerName: 'Model', flex: 1 },
    { field: 'year', headerName: 'Year', flex: 0.7 },
    { field: 'inspector', headerName: 'Inspector', flex: 1.2 },
    { field: 'date', headerName: 'Date', flex: 1 },
    { field: 'status', headerName: 'Status', cellRenderer: StatusCellRenderer, flex: 1 },
  ], [])

  if (error) {
    return <div className="text-destructive">Error loading reports: {error.message}</div>
  }

  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4">Manufacturing Reports</h2>
      <div className="ag-theme-alpine" style={{ height: 600 }}>
        <AgGridReact<ReportDTO>
          rowData={reports ?? []}
          columnDefs={columnDefs}
          loading={isLoading}
          pagination
          paginationPageSize={20}
          domLayout="normal"
        />
      </div>
    </div>
  )
}
