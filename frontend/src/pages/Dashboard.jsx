import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function Dashboard() {
  const [user, setUser] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const u = localStorage.getItem('user')
    if (u) {
      setUser(JSON.parse(u))
    } else {
      navigate('/login')
    }
  }, [navigate])

  const logout = () => {
    localStorage.removeItem('user')
    setUser(null)
    navigate('/login', { replace: true })
  }

  if (!user) return null

  return (
    <div style={{ padding: 24 }}>
      <h2>Dashboard</h2>
      <p>Welcome, <strong>{user.username || user.email}</strong></p>
      <button onClick={logout}>Logout</button>
    </div>
  )
}
