package ar.unq.desapp.grupob.backenddesappapi.repository
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity,Long> {
    fun findByEmail(username: String): UserEntity?
}