package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class PostServiceImpl: PostService {
    override fun intentPost(aPost: Post) {
        TODO("Not yet implemented")
    }

}