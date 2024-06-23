package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoOperation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface CryptoOperationRepository: JpaRepository<CryptoOperation, Long> {
    @Query("SELECT c FROM CryptoOperation c WHERE (c.dateTime BETWEEN :startDate AND :endDate) AND (c.post.owner.id = :userId OR c.client.id = :userId)")
    fun getByOwnerBetweenDates(@Param("userId")userId: Long,  @Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<CryptoOperation>
}