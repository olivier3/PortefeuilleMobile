package cstjean.mobile.portefeuille.travail

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class CreditCard(
    @PrimaryKey val id: UUID,
    val nom: String,
    val cardNumbers: Int,
    val expDate: Date,
    )
