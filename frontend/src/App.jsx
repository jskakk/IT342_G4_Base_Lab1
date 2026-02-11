import { useState } from 'react'
import axios from 'axios'
import './App.css'

function App() {
  // 1. Variables to hold user input
  const [username, setUsername] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [message, setMessage] = useState("")

  // 2. Function to send data to Backend
  const handleRegister = async (e) => {
    e.preventDefault(); // Stop page refresh
    
    try {
      // This sends a POST request to your Java Controller
      // Change this line:
        const response = await axios.post('http://localhost:8080/api/users/register', {
        username: username,
        email: email,
        password: password
      });
      
      setMessage("Success! User ID: " + response.data.id);
    } catch (error) {
      console.error(error);
      setMessage("Error: Could not register. Is Backend running?");
    }
  }

  return (
    <div style={{ padding: '50px' }}>
      <h1>Registration System</h1>
      
      <form onSubmit={handleRegister} style={{ display: 'flex', flexDirection: 'column', gap: '10px', maxWidth: '300px' }}>
        <input 
          type="text" 
          placeholder="Username" 
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input 
          type="email" 
          placeholder="Email" 
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input 
          type="password" 
          placeholder="Password" 
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Register</button>
      </form>

      {/* Show success or error message */}
      <p style={{ color: message.includes("Error") ? 'red' : 'green' }}>
        {message}
      </p>
    </div>
  )
}

export default App