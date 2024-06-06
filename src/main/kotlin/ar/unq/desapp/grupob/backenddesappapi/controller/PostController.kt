package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.PostDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.ResponsePostDTO
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.service.PostService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import ar.unq.desapp.grupob.backenddesappapi.utils.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @Autowired
    lateinit var postService: PostService
    @Autowired
    lateinit var dolarApiService: DolarApiService

    @PostMapping("/post/{userId}")
    fun postIntent(@PathVariable userId: String, @RequestBody postDTO: PostDTO): ResponsePostDTO {
        val post: Post = Mapper.fromDtoToPost(postDTO)
        val createdPost = postService.intentPost(post, userId.toLong())
        val dolarPriceInArs = dolarApiService.getDolarCryptoPrice()
        var response = Mapper.fromPostToResponsePostDTO(createdPost)
        response.priceInArs = dolarPriceInArs!!.compra.toFloat() * createdPost.amount!!
        return response
    }

    @GetMapping("/activePosts")
    fun getActivePosts(): List<ResponsePostDTO> {
        val posts = postService.getActivePost()
        val dolarPriceInArs = dolarApiService.getDolarCryptoPrice()
        return posts.map {
            var dto = Mapper.fromPostToResponsePostDTO(it)
            dto.priceInArs = dolarPriceInArs!!.compra.toFloat() * it.amount!!
            dto
        }
    }
}