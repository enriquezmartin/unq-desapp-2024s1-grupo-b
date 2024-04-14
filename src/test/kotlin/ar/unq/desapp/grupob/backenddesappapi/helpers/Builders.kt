package ar.unq.desapp.grupob.backenddesappapi.helpers

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity

class UserBuilder(){

    private var id: Long? = null
    private var name: String? = null
    private var username: String? = null
    private var address: String? = null
    private var surname: String? = null
    private var email: String? = null
    private var password: String? = null
    private var cvuMP: String? = null
    private var walletAddress: String? = null

    fun build(): UserEntity{
        val user: UserEntity = UserEntity(id, username, password, name, surname, email, address, cvuMP, walletAddress)
        return user
    }

    fun withId(id: Long): UserBuilder{
        this.id = id
        return this
    }

    fun withName(name: String): UserBuilder {
        this.name = name
        return this
    }

    fun withUsername(username: String): UserBuilder {
        this.username = username
        return this
    }

    fun withAddress(address: String): UserBuilder {
        this.address = address
        return this
    }

    fun withSurname(surname: String): UserBuilder {
        this.surname = surname
        return this
    }

    fun withEmail(email: String): UserBuilder {
        this.email = email
        return this
    }

    fun withPassword(password: String): UserBuilder {
        this.password = password
        return this
    }

    fun withCvuMP(cvuMP: String): UserBuilder {
        this.cvuMP = cvuMP
        return this
    }

    fun withWalletAddress(walletAddress: String): UserBuilder {
        this.walletAddress = walletAddress
        return this
    }

}