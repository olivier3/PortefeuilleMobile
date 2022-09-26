package cstjean.mobile.portefeuille.creditcard

import android.text.format.DateFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class CreditCard(
    @PrimaryKey val id: UUID,
    val nom: String,
    val cardNumbers: Int,
    val expDate: Date
    ) {
    val expDateFormatee
        get() = DateFormat.format("EEEE, MM yy", expDate).toString()
}
