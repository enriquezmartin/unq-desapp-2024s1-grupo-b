package ar.unq.desapp.grupob.backenddesappapi.utils

import ar.unq.desapp.grupob.backenddesappapi.dtos.PostDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.ResponsePostDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.UserDTO
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.Post

object Mapper {
    fun fromDtoToPost(dto: PostDTO): Post {
        var post: Post = Post()
        post.price = dto.price
        post.cryptoCurrency = CryptoCurrency.valueOf(dto.cryptoCurrency)
        post.operationType = OperationType.valueOf(dto.operation)
        post.amount = dto.amount
        return post
    }

    fun fromPostToResponsePostDTO(post: Post): ResponsePostDTO{
        var dto = ResponsePostDTO(
            post.id!!,
            post.cryptoCurrency.toString(),
            post.amount!!,
            post.price!!,
            UserDTO(post.owner!!.name!!, post.owner!!.surname!!),
            post.operationType.toString(),
            post.createdDate!!
        )
        return dto
    }
}