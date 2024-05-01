package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.Post

interface PostService {
    fun intentPost(aPost: Post)
}