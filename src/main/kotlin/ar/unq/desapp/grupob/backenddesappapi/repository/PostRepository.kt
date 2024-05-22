package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.PostStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {

    fun findByStatus(statusPost: PostStatus): List<Post>
}