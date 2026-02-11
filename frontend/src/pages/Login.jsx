import { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

export default function Login() {
  const [identifier, setIdentifier] = useState('')
  const [password, setPassword] = useState('')
  const [errors, setErrors] = useState({})
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const validate = () => {
    const e = {}
    if (!identifier) e.identifier = 'Enter username or email'
    if (!password) e.password = 'Enter your password'
    setErrors(e)
    return Object.keys(e).length === 0
  }

  const handleLogin = async (ev) => {
    ev.preventDefault()
    if (!validate()) return
    setLoading(true)
    try {
      const resp = await axios.get('http://localhost:8080/api/users')
      const users = resp.data || []
      const found = users.find(u => (u.username === identifier || u.email === identifier) && u.password === password)
      if (found) {
        localStorage.setItem('user', JSON.stringify(found))
        navigate('/dashboard')
      } else {
        setErrors({ auth: 'Invalid credentials' })
      }
    } catch (err) {
      console.error(err)
      setErrors({ server: 'Error: Could not contact backend' })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="split">
      <div className="left">
        <div className="card">
          <h2 className="title">Sign in</h2>
          <form onSubmit={handleLogin} className="form">
            <input className="big-input" placeholder="Username or Email" value={identifier} onChange={(e) => setIdentifier(e.target.value)} />
            {errors.identifier && <div className="error">{errors.identifier}</div>}

            <input className="big-input" placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            {errors.password && <div className="error">{errors.password}</div>}

            <button className="primary" type="submit" disabled={loading}>{loading ? 'Signing in…' : 'Sign in'}</button>

            {errors.auth && <div className="error">{errors.auth}</div>}
            {errors.server && <div className="error">{errors.server}</div>}
          </form>
        </div>
      </div>
    </div>
  )
}
