package com.aula.desafio_mobile

class Admin {
    private var user: String
    private var password: String

    constructor(user: String, password: String) {
        this.user = user
        this.password = password
    }

    fun getUser(): String {
        return this.user
    }

    fun getPassword(): String {
        return this.password
    }

    fun setUser(user: String) {
        this.user = user
    }

    fun setPassword(password: String) {
        this.password = password
    }
}