package cstjean.mobile.portefeuille.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cstjean.mobile.portefeuille.creditcard.CreditCard
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Interface des requêtes BD
 *
 * @author Olivier Bilodeau et Laura Tram
 */
@Dao
interface CreditCardDao {
    @Query("SELECT * FROM creditcard")
    fun getCreditCards(): Flow<List<CreditCard>>

    @Query("SELECT * FROM creditcard WHERE id=(:id)")
    suspend fun getCreditCard(id: UUID): CreditCard

    @Insert
    suspend fun addCreditCard(creditCard: CreditCard)

    @Update
    suspend fun updateCreditCard(creditCard: CreditCard)
}