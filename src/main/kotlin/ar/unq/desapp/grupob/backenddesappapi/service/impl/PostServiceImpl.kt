package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.StatusPost
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.PostService
import ar.unq.desapp.grupob.backenddesappapi.utils.PriceOutOfRangeException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class PostServiceImpl: PostService {

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var userRepository: UserRepository
    override fun intentPost(aPost: Post, id: Long): Post {
        var lastPrice = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(aPost.cryptoCurrency!!)
        var priceIsExceeded = aPost.price!! > lastPrice.value!! * 1.05F
        var priceFallShort = aPost.price!! < lastPrice.value!! * 0.95F
        if(priceIsExceeded || priceFallShort){
            throw PriceOutOfRangeException()
        }
        else{
            val user = userRepository.findById(id).getOrElse {
                throw UsernameNotFoundException("The user with id $id was not found")
            }
            aPost.status = StatusPost.ACTIVE
            user.addPost(aPost)
            userRepository.save(user)
            return aPost
        }
    }

    override fun getActivePost(): List<Post> {
        return postRepository.findByStatus(StatusPost.ACTIVE)
    }

    override fun saveAllPosts(posts: List<Post>) {
        postRepository.saveAll(posts)
    }

}