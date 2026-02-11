import { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

export default function Register() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirm, setConfirm] = useState('')
  const [errors, setErrors] = useState({})
  const [loading, setLoading] = useState(false)
  const [success, setSuccess] = useState('')
  const navigate = useNavigate()

  const validate = () => {
    const e = {}
    if (!username || username.length < 3) e.username = 'Username must be at least 3 characters'
    if (!email || !/^\S+@\S+\.\S+$/.test(email)) e.email = 'Enter a valid email'
    if (!password || password.length < 6) e.password = 'Password must be at least 6 characters'
    if (password !== confirm) e.confirm = 'Passwords do not match'
    setErrors(e)
    return Object.keys(e).length === 0
  }

  const handleRegister = async (ev) => {
    ev.preventDefault()
    if (!validate()) return
    setLoading(true)
    try {
      await axios.post('http://localhost:8080/api/users/register', { username, email, password })
      setSuccess('Registration successful — redirecting to login...')
      setTimeout(() => navigate('/login', { replace: true }), 800)
    } catch (err) {
      console.error(err)
      setErrors({ server: 'Could not register. Is backend running?' })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="split">
      <div className="left">
        <div className="card">
          <h2 className="title">Registration</h2>
          <form onSubmit={handleRegister} className="form">
            <input className="big-input" placeholder="Enter your name" value={username} onChange={(e) => setUsername(e.target.value)} />
            {errors.username && <div className="error">{errors.username}</div>}

            <input className="big-input" placeholder="Enter your email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
            {errors.email && <div className="error">{errors.email}</div>}

            <input className="big-input" placeholder="Create password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            {errors.password && <div className="error">{errors.password}</div>}

            <input className="big-input" placeholder="Confirm password" type="password" value={confirm} onChange={(e) => setConfirm(e.target.value)} />
            {errors.confirm && <div className="error">{errors.confirm}</div>}

            <button className="primary" type="submit" disabled={loading}>{loading ? 'Registering…' : 'Register Now'}</button>

            {errors.server && <div className="error">{errors.server}</div>}
            {success && <div className="success">{success}</div>}
          </form>
        </div>
      </div>
    </div>
  )
}
