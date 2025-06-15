package com.aula.desafio_mobile

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.type.DateTime

class AdapterAtendimento(
    private val atendimentos: MutableList<Atendimento> = mutableListOf(),
    private val isAdmin: Boolean = false
) : RecyclerView.Adapter<AdapterAtendimento.ViewHolder>() {
    private val db = Database()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAtendimento.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_atendimento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return atendimentos.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nome.text = atendimentos[position].getNome()
        holder.entrada.text = "Entrada: " + atendimentos[position].getEntrada()

        val saida = atendimentos[position].getSaida()
        holder.saida.visibility = View.GONE
        if (saida != "") {
            holder.saida.text = "Saída: $saida"
            holder.saida.visibility = View.VISIBLE
        }

        holder.itemView.setOnLongClickListener {
            if (saida == "") {
                val caixaAlert = Dialog(holder.itemView.context)
                caixaAlert.setContentView(R.layout.finalizar_atendimento)
                caixaAlert.window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                caixaAlert.setCancelable(false)
                caixaAlert.window?.setBackgroundDrawableResource(android.R.color.transparent)

                val nao = caixaAlert.findViewById<Button>(R.id.nao)
                val sim = caixaAlert.findViewById<Button>(R.id.sim)

                nao.setOnClickListener {
                    caixaAlert.dismiss()
                }
                sim.setOnClickListener {
                    // Atualizar objeto da lista
                    val atendimento = atendimentos[position]

                    val dataHoraAtual = java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    )
                    atendimento.setSaida(dataHoraAtual)
                    db.salvar(atendimento, holder.itemView.context)
                    caixaAlert.dismiss()
                    notifyItemChanged(position)
                }
                caixaAlert.show()
                true
            } else {
                Toast.makeText(holder.itemView.context, "Atendimento já finalizado!", Toast.LENGTH_SHORT).show()
                false
            }
        }

        holder.itemView.setOnClickListener {
            if (isAdmin) {
                val atendimento = atendimentos[position]
                db.remover(atendimento, holder.itemView.context)
                atendimentos.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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