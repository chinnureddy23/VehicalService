package com.example.vechiceserviceapp

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vechiceserviceapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Move to LoginActivity
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                // Show success toast message
                                showToast("Account created successfully")
                            } else {
                                showToast("Failed to create account: ${task.exception?.message}")
                            }
                        }
                } else {
                    showToast("Password does not match")
                }
            } else {
                showToast("Fields cannot be empty")
            }
        }

        binding.loginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            togglePasswordVisibility(isChecked)
        }
    }

    private fun togglePasswordVisibility(isVisible: Boolean) {
        val passwordEditText = binding.signupPassword
        val confirmPasswordEditText = binding.signupConfirm

        if (isVisible) {
            // Show passwords
            passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            confirmPasswordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            // Hide passwords
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            confirmPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
