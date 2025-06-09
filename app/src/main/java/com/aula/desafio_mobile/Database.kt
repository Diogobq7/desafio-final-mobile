package com.aula.desafio_mobile

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Database {

    fun Database() {}

    fun abrirDB():FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    fun salvar(argAtendimento: Atendimento, c: Context) {
        val db = abrirDB()

        // Registro NOVO
        db.collection("atendimento").document(argAtendimento.getId().toString())
            .set(argAtendimento)
        Toast.makeText(c, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
    }
}