    package com.aula.desafio_mobile

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.google.firebase.firestore.FirebaseFirestore
    import kotlinx.coroutines.tasks.await
    import androidx.lifecycle.lifecycleScope
    import kotlinx.coroutines.launch


    class Login : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_login)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            var btn = findViewById<Button>(R.id.iniciar_atendimento)
            btn.setOnClickListener {
                val inputCracha = findViewById<EditText>(R.id.input_num)
                val cracha = inputCracha.text.toString()

                lifecycleScope.launch {
                    val funcionario = buscarFuncionarioPorCracha(cracha)
                    if (funcionario != null) {
                        val intent = Intent(this@Login, MainActivity::class.java)
                        intent.putExtra("nome", funcionario.getNome())
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@Login, "Funcionario não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        suspend fun buscarFuncionarioPorCracha(cracha: String): Funcionario? {
            val db = FirebaseFirestore.getInstance()
            val querySnapshot = db.collection("funcionario")
                .whereEqualTo("cracha", cracha).get().await()
            if (!querySnapshot.isEmpty) {
                val doc = querySnapshot.documents[0]
                val nome = doc.getString("nome") ?: return null
                val crachaEncontrado = doc.getString("cracha") ?: return null
                return Funcionario(nome, crachaEncontrado)
            }
            return null
        }
    }