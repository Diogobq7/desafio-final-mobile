package com.aula.desafio_mobile

class Atendimento {
    private var id: String = ""
    private lateinit var nome: String
    private lateinit var entrada: String
    private lateinit var saida: String

    constructor() {}

    constructor(nome: String, entrada: String, saida: String) {
        this.nome = nome
        this.entrada = entrada
        this.saida = saida
    }

    fun getId(): String {
        return this.id
    }

    fun getNome(): String {
        return this.nome
    }

    fun getEntrada(): String {
        return this.entrada
    }

    fun getSaida(): String {
        return this.saida
    }

    fun setId(id: String) {
        this.id = id
    }

    fun setNome(nome: String) {
        this.nome = nome
    }

    fun setEntrada(entrada: String) {
        this.entrada = entrada
    }

    fun setSaida(saida: String) {
        this.saida = saida
    }
}