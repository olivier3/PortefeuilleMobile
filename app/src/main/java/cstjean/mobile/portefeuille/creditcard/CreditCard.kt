package cstjean.mobile.portefeuille.creditcard

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Une carte de credit.
 *
 * @property id Le ID de la carte de credit.
 * @property nom Le nom du détenteur de la carte de credit.
 * @property cardNumbers Les numéros de la carte de credit.
 * @property expDate La date d'expiration de la carte de credit.
 *
 * @author Olivier Bilodeau et Laura Tram
 */
@Entity
data class CreditCard constructor(
    @PrimaryKey val id: UUID,
    val nom: String,
    val cardNumbers: String,
    val expDate: String
    )
