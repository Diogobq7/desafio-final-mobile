package com.aula.desafio_mobile

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext

class AdapterAtendimento(private val atendimentos: MutableList<Atendimento> = mutableListOf()) : RecyclerView.Adapter<AdapterAtendimento.ViewHolder>() {
    private val db = Database()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAtendimento.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_atendimento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return atendimentos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nome.text = atendimentos[position].getNome()
        holder.entrada.text = atendimentos[position].getEntrada()
        holder.saida.text = atendimentos[position].getSaida()

        holder.itemView.setOnLongClickListener {
            val caixaAlert = Dialog(holder.itemView.context)
            caixaAlert.setContentView(R.layout.finalizar_atendimento)
            caixaAlert.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            caixaAlert.setCancelable(false)
            caixaAlert.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val nao = caixaAlert.findViewById<Button>(R.id.nao)
            val sim = caixaAlert.findViewById<Button>(R.id.sim)

            nao.setOnClickListener {
                caixaAlert.dismiss()
            }
            sim.setOnClickListener {
                // Atualizar objeto da lista
                val atendimento = atendimentos[position].setSaida("agora foi bixo") as Atendimento
                db.salvar(atendimento, holder.itemView.context)
                caixaAlert.dismiss()
            }
            caixaAlert.show()
            true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // O ViewHolder indica quais são os componentes do cardView
        var nome: TextView
        var entrada: TextView
        var saida: TextView
        init {
            nome = itemView.findViewById(R.id.item_atendimento_nome)
            entrada = itemView.findViewById(R.id.item_atendimento_entrada)
            saida = itemView.findViewById(R.id.item_atendimento_saida)
        }
    }
}