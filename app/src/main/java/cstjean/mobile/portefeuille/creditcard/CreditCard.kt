package cstjean.mobile.portefeuille.creditcard

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class CreditCard constructor(
    @PrimaryKey val id: UUID,
    val nom: String,
    val cardNumbers: String,
    val expDate: String
    )
