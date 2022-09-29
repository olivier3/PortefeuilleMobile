package cstjean.mobile.portefeuille

/**
 * Data class pour la création d'une carte format json
 *
 * @author Olivier Bilodeau et Laura Tram
 */
class CreditCardData(
    val nom: String,
    val carte: String,
    val expiration: String
) {
    override fun toString(): String {
        return "nom: ${this.nom}, carte: ${this.carte}, expiration: ${this.expiration}"
    }
}