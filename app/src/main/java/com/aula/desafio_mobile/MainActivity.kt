package com.aula.desafio_mobile

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aula.desafio_mobile.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = Database()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        FirebaseApp.initializeApp(applicationContext)

        // Configuração da navegação
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_admin
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.floatingActionButton.setOnClickListener {
            showAddUserDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!::appBarConfiguration.isInitialized) {
            return super.onSupportNavigateUp()
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showAddUserDialog() {
        val dialog = Dialog(this).apply {
            setContentView(R.layout.adicionar_atendimento)
            window?.setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(true)
        }

        val editTextUserName = dialog.findViewById<TextInputEditText>(R.id.nomeFuncio)
        val buttonAdd = dialog.findViewById<Button>(R.id.buttonAddUser)
        val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancel)

        buttonAdd.setOnClickListener {
            val userName = editTextUserName.text?.toString()?.trim()
            if (!userName.isNullOrEmpty()) {
                // Criar novo atendimento
                val currentDateTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                )

                val newAtendimento = Atendimento().apply {
                    setNome(userName)
                    setEntrada(currentDateTime)
                    setSaida("") // Sem data de término
                }

                // Salvar no Firestore
                db.salvar(newAtendimento, this@MainActivity)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Por favor, digite um nome válido", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}