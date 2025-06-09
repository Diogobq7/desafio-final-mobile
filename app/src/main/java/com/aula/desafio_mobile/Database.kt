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

        db.collection("atendimento").document(argAtendimento.getId().toString())
            .set(argAtendimento)
        Toast.makeText(c, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
    }

    fun remover(argAtendimento: Atendimento, c: Context) {
        val db = abrirDB()

        db.collection("atendimento").document(argAtendimento.getId().toString())
            .delete().addOnSuccessListener {
                Toast.makeText(c, "Nota removida com sucesso!", Toast.LENGTH_SHORT).show()
            }
    }

    fun listar(argAtendimento: MutableList<Atendimento>, argAdapter: AdapterAtendimento, c: Context){

    }


}