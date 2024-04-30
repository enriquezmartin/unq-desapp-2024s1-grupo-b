package ar.unq.desapp.grupob.backenddesappapi.repository
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<UserEntity,Long> {
    fun findByEmail(username: String): Optional<UserEntity>;
}