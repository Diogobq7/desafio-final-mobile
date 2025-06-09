package com.aula.desafio_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterAtendimento(private val notas: MutableList<Atendimento> = mutableListOf()) : RecyclerView.Adapter<AdapterAtendimento.ViewHolder>() {
    private val db = Database()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAtendimento.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout., parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
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