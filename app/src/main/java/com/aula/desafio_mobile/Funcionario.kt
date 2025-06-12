package com.aula.desafio_mobile

class Funcionario {
    private var nome: String
    private var cracha: String

    constructor(nome: String, cracha: String) {
        this.nome = nome
        this.cracha = cracha
    }

    fun getNome(): String {
        return this.nome
    }

    fun getCracha(): String {
        return this.cracha
    }

    fun setNome(nome: String) {
        this.nome = nome
    }

    fun setCracha(cracha: String) {
        this.cracha = cracha
    }
}