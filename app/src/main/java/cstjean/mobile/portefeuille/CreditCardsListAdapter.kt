package cstjean.mobile.portefeuille

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.databinding.ListItemCreditCardBinding
import java.util.UUID

class CreditCardsListAdapter(
    private val creditCards: List<CreditCard>,
    private val onCreditCardClicked: (creditCardId: UUID) -> Unit) :
    RecyclerView.Adapter<CreditCardHolder>() {

    /**
     * Lors de la création des ViewHolder.
     *
     * @param parent Layout dans lequel la nouvelle vue
     *                 sera ajoutée quand elle sera liée à une position.
     * @param viewType Le type de vue de la nouvelle vue.
     *
     * @return Un ViewHolder pour notre cellule.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCreditCardBinding.inflate(inflater, parent, false)
        return CreditCardHolder(binding)
    }

    /**
     * Associe un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à utiliser.
     * @param position La position dans la liste qu'on souhaite utiliser.
     */
    override fun onBindViewHolder(holder: CreditCardHolder, position: Int) {
        val creditCard = creditCards[position]
        holder.bind(creditCard, onCreditCardClicked)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = creditCards.size
}

class CreditCardHolder(private val binding: ListItemCreditCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe un travail à ce ViewHolder.
     *
     * @param travail Le travail associé.
     */
    fun bind(creditCards: CreditCard, onCreditCardClicked: (creditCardId: UUID) -> Unit) {
        binding.cardOwner.text = creditCards.nom


        val lastDigit = creditCards.cardNumbers.substring(15)
        val cardNumbers = "****-****-****-$lastDigit"
        binding.cardDigits.text = cardNumbers

        binding.cardExpirationDate.text = creditCards.expDate

        if (creditCards.cardNumbers[0] == '4') {
            binding.cardType.setImageResource(R.drawable.visa_svgrepo_com)
        } else {
            binding.cardType.setImageResource(R.drawable.mastercard_svgrepo_com)
        }

        binding.root.setOnClickListener {
            onCreditCardClicked(creditCards.id)
        }
    }
}