package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity

interface UserService {

    fun register(userEntity: UserEntity): UserEntity
}