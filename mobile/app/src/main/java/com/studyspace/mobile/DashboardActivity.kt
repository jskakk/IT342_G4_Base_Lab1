package com.studyspace.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studyspace.mobile.api.AuthResponse
import com.studyspace.mobile.api.RetrofitClient
import com.studyspace.mobile.databinding.ActivityDashboardBinding
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDashboardBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        loadUserInfo()
        loadAllUsers()
        
        binding.btnLogout.setOnClickListener {
            logout()
        }
        
        binding.btnRefresh.setOnClickListener {
            loadAllUsers()
        }
    }
    
    private fun loadUserInfo() {
        val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "User")
        val email = prefs.getString("email", "")
        
        binding.tvWelcome.text = "Welcome, $username!"
        binding.tvEmail.text = email
    }
    
    private fun loadAllUsers() {
        binding.btnRefresh.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getAllUsers()
                
                if (response.isSuccessful && response.body() != null) {
                    val users = response.body()!!
                    displayUsers(users)
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to load users", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.btnRefresh.isEnabled = true
            }
        }
    }
    
    private fun displayUsers(users: List<AuthResponse>) {
        val usersList = users.joinToString("\n\n") { 
            "• ${it.username}\n  ${it.email}" 
        }
        binding.tvUsersList.text = if (users.isEmpty()) {
            "No users registered yet"
        } else {
            "Registered Users (${users.size}):\n\n$usersList"
        }
    }
    
    private fun logout() {
        val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
