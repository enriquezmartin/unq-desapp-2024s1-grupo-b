package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.StatusPost
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun `get posts by status`(){
        val activePost = PostBuilder().withId(1L).withStatus(StatusPost.ACTIVE).build()
        val inactivePost = PostBuilder().withId(2L).withStatus(StatusPost.CLOSED).build()
        val inProgressPost = PostBuilder().withId(3L).withStatus(StatusPost.IN_PROGRESS).build()
        val posts: List<Post> = listOf(activePost, inactivePost, inProgressPost)
        postRepository.saveAll(posts)

        val result = postRepository.findByStatus(StatusPost.ACTIVE)

        assertTrue(result.any { it.id!! == activePost.id })
    }
}