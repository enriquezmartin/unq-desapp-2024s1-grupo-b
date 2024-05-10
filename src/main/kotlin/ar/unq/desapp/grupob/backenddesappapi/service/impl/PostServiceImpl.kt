package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.*
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
    override fun intentPost(post: Post, id: Long): Post {
        var lastPrice = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)
        var priceIsExceeded = post.price!! > lastPrice.value!! * 1.05F
        var priceFallShort = post.price!! < lastPrice.value!! * 0.95F
        if(priceIsExceeded || priceFallShort){
            throw PriceOutOfRangeException()
        }
        else{
            val user = userRepository.findById(id).getOrElse {
                throw UsernameNotFoundException("The user with id $id was not found")
            }
            post.status = PostStatus.ACTIVE
            user.addPost(post)
            userRepository.save(user)
            return post
        }
    }

    override fun getActivePost(): List<Post> {
        return postRepository.findByStatus(PostStatus.ACTIVE)
    }


}