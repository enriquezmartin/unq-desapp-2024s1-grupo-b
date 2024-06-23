package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.PostStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @AfterEach
    fun clean() {
        postRepository.deleteAll()
        //dataService.cleanUp()
    }

    @Test
    fun `get posts by status`(){
        val activePost = PostBuilder().withStatus(PostStatus.ACTIVE).build()
        val inactivePost = PostBuilder().withStatus(PostStatus.CLOSED).build()
        val inProgressPost = PostBuilder().withStatus(PostStatus.IN_PROGRESS).build()
        val posts: List<Post> = listOf(activePost, inactivePost, inProgressPost)
        postRepository.saveAll(posts)

        val result = postRepository.findByStatus(PostStatus.ACTIVE)

        assertTrue(result.any { it.id!! == activePost.id })
    }
}