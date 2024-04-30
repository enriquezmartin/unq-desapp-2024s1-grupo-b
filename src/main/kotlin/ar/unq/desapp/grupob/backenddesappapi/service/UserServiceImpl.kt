package ar.unq.desapp.grupob.backenddesappapi.service


import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.utils.UsernameAlreadyTakenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl : UserService{

    @Autowired
    lateinit var userDao: UserRepository
    override fun register(user: UserEntity): UserEntity {
        try{
            println(user.email)
            return userDao.save(user)
        }catch (e: DataIntegrityViolationException){
            println(e.message)
            throw UsernameAlreadyTakenException("Email ${user.email} has already taken")
        }
    }


}