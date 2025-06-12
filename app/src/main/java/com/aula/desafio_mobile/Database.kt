package com.aula.desafio_mobile

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Database {

    init {}

    fun abrirDB():FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    fun salvar(argAtendimento: Atendimento, c: Context) {
        val db = abrirDB()

        // Se não tiver ID, cria um novo documento
        if (argAtendimento.getId().isEmpty()) {
            val docRef = db.collection("atendimento").document()
            argAtendimento.setId(docRef.id) // Define o ID no objeto
        }

        // Converte para Map
        val atendimentoMap = hashMapOf(
            "nome" to argAtendimento.getNome(),
            "entrada" to argAtendimento.getEntrada(),
            "saida" to argAtendimento.getSaida()
        )

        db.collection("atendimento").document(argAtendimento.getId())
            .set(atendimentoMap)
            .addOnSuccessListener {
                Toast.makeText(c, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(c, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
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

        db.collection("atendimento")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("Firestore", "Erro ao ouvir mudanças", error)
                    Toast.makeText(c, "Você está off-line neste momento!", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d("Firestore", "Snapshot recebido com ${snapshot.documents.size} documentos")

                    argAtendimento.clear()

                    for (document in snapshot.documents) {
                        Log.d("Firestore", "Documento: ${document.data}")

                        val atendimento = Atendimento().apply {
                            setId(document.id)
                            setNome(document.getString("nome") ?: "")
                            setEntrada(document.getString("entrada") ?: "")
                            setSaida(document.getString("saida") ?: "")
                        }
                        argAtendimento.add(atendimento)
                    }

                    Log.d("Firestore", "Lista atualizada com ${argAtendimento.size} atendimentos")
                    argAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Firestore", "Nenhum dado encontrado")
                    Toast.makeText(c, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun listarPorMes(argAtendimento: MutableList<Atendimento>, argAdapter: AdapterAtendimento, c: Context){
        val db = abrirDB()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dataLimite = LocalDate.now().minusMonths(1)

        db.collection("atendimento")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("Firestore", "Erro ao ouvir mudanças", error)
                    Toast.makeText(c, "Você está off-line neste momento!", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d("Firestore", "Snapshot recebido com ${snapshot.documents.size} documentos")

                    argAtendimento.clear()

                    for (document in snapshot.documents) {
                        Log.d("Firestore", "Documento: ${document.data}")

                        val entradaStr = document.getString("entrada")?.trim() ?: continue

                        val dataEntrada = try {
                            LocalDate.parse(entradaStr, formatter)
                        } catch (e: Exception) {
                            Log.e("Firestore", "Data inválida: $entradaStr")
                            continue
                        }

                        if (dataEntrada.isAfter(dataLimite)) {
                            continue
                        }

                        val atendimento = Atendimento().apply {
                            setId(document.id)
                            setNome(document.getString("nome") ?: "")
                            setEntrada(entradaStr)
                            setSaida(document.getString("saida") ?: "")
                        }
                        argAtendimento.add(atendimento)
                    }

                    Log.d("Firestore", "Lista atualizada com ${argAtendimento.size} atendimentos")
                    argAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Firestore", "Nenhum dado encontrado")
                    Toast.makeText(c, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show()
                }
            }
    }

}