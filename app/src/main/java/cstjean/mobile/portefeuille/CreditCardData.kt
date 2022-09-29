package cstjean.mobile.portefeuille

class CreditCardData(
    val nom: String,
    val carte: String,
    val expiration: String
) {
    override fun toString(): String {
        return "nom: ${this.nom}, carte: ${this.carte}, expiration: ${this.expiration}"
    }
}