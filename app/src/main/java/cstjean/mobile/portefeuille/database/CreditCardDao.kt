package cstjean.mobile.portefeuille.database

import androidx.room.Dao
import androidx.room.Query
import cstjean.mobile.portefeuille.creditcard.CreditCard
import java.util.UUID

@Dao
interface CreditCardDao {
    @Query("SELECT * FROM creditcard")
    fun getCreditCards(): List<CreditCard>

    @Query("SELECT * FROM creditcard WHERE id=(:id)")
    fun getCreditCard(id: UUID): CreditCard
}