import { Routes, Route } from 'react-router-dom'
import ReportListPage from '@/pages/ReportListPage'
import ReportDetailPage from '@/pages/ReportDetailPage'

function App() {
  return (
    <div className="min-h-screen bg-background text-foreground">
      <header className="border-b px-6 py-4">
        <h1 className="text-xl font-bold">Full Stack Reports</h1>
      </header>
      <main className="p-6">
        <Routes>
          <Route path="/" element={<ReportListPage />} />
          <Route path="/reports/:id" element={<ReportDetailPage />} />
        </Routes>
      </main>
    </div>
  )
}

export default App
