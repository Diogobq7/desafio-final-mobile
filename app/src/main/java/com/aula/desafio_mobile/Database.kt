package com.aula.desafio_mobile

import android.content.Context
import android.util.Log
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
                Toast.makeText(c, "Atendimento removido com sucesso!", Toast.LENGTH_SHORT).show()
            }
    }

    fun listar(argAtendimento: MutableList<Atendimento>, argAdapter: AdapterAtendimento, c: Context){
        val db = abrirDB()

        db.collection("ListaNotas")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("Firestore", "Erro ao ouvir mudanças", error)
                    Toast.makeText(c, "Você está off-line neste momento!", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    argAtendimento.clear() // Limpa a lista antes de adicionar os novos itens

                    for (document in snapshot.documents) {
                        val atendimento = document.toObject(Atendimento::class.java)
                        atendimento?.let {
                            argAtendimento.add(it)
                        }
                    }

                    argAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Firestore", "Nenhum dado encontrado")
                    Toast.makeText(c, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
                }
            }
    }


}