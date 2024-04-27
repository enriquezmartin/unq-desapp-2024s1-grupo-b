package ar.unq.desapp.grupob.backenddesappapi.model

import java.time.LocalDate

class CryptoOperation(var id:Long?, post: Post, interestedUser: UserEntity, dateTime: LocalDate) {
    constructor(post: Post, interestedUser: UserEntity, dateTime: LocalDate) : this(null, post, interestedUser, dateTime)
}