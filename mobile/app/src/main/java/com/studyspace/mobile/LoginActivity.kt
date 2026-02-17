package com.studyspace.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studyspace.mobile.api.AuthRequest
import com.studyspace.mobile.api.RetrofitClient
import com.studyspace.mobile.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Check if already logged in
        val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        if (prefs.contains("userId")) {
            navigateToDashboard()
            return
        }
        
        binding.btnLogin.setOnClickListener {
            val identifier = binding.etIdentifier.text.toString()
            val password = binding.etPassword.text.toString()
            
            if (identifier.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            login(identifier, password)
        }
        
        binding.tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    private fun login(identifier: String, password: String) {
        binding.btnLogin.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.login(
                    AuthRequest(identifier, password)
                )
                
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    
                    // Save user data
                    val prefs = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putLong("userId", user.id)
                        putString("username", user.username)
                        putString("email", user.email)
                        apply()
                    }
                    
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    navigateToDashboard()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    binding.btnLogin.isEnabled = true
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                binding.btnLogin.isEnabled = true
            }
        }
    }
    
    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
