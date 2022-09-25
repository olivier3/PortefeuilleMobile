package cstjean.mobile.ecole.travail

import android.text.format.DateFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Un travail scolaire.
 *
 * @property id Le ID du travail.
 * @property nom Le nom du travail.
 * @property dateRemise La date de remise du travail.
 * @property estTermine Si le travail est terminé.
 *
 * @author Gabriel T. St-Hilaire
 */
@Entity
data class Travail(
    @PrimaryKey val id: UUID,
    val nom: String,
    val dateRemise: Date,
    val estTermine: Boolean,
    var coequipier: String = ""
    ){
    val dateRemiseFormatee
        get() = DateFormat.format("EEEE, dd MMMM yyyy", dateRemise).toString()
}
