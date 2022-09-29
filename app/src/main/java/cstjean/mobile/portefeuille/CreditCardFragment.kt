package cstjean.mobile.portefeuille

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.databinding.FragmentCreditCardBinding
import kotlinx.coroutines.launch

class CreditCardFragment : Fragment() {
    private var _binding: FragmentCreditCardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val args: CreditCardFragmentArgs by navArgs()

    private val creditCardViewModel: CreditCardViewModel by viewModels {
        CreditCardViewModelFactory(args.creditCardId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditCardBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardOwnerRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()
        val cardNumberRegex = "([0-9]{4}-){3}[0-9]{3}L".toRegex()
        val cardExpiryDateRegex = "^([1-9]|1[0-2])\\/?([0-9]{4})\$".toRegex()

        binding.apply {
            cardOwner.doOnTextChanged { text, _, _, _ ->
                if (cardOwnerRegex.containsMatchIn(cardOwner.text)) {
                    creditCardViewModel.updateCreditCard { oldCreditCard ->
                        oldCreditCard.copy(nom = text.toString())
                    }
                }
            }

            cardNumbers.doOnTextChanged { text, _, _, _ ->
                if (cardNumberRegex.containsMatchIn(cardNumbers.text)) {
                    creditCardViewModel.updateCreditCard { oldCreditCard ->
                        oldCreditCard.copy( cardNumbers = text.toString())
                    }
                }
            }

            cardExpirationDate.doOnTextChanged { text, _, _, _ ->
                if (cardExpiryDateRegex.containsMatchIn(cardExpirationDate.text)) {
                    creditCardViewModel.updateCreditCard { oldCreditCard ->
                        oldCreditCard.copy( expDate = text.toString())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                creditCardViewModel.creditCard.collect { creditCard ->
                    creditCard?.let { updateUi(it) }
                }
            }
        }
    }

    private fun updateUi(creditCard: CreditCard) {

        binding.apply {

            if (cardOwner.text.toString() != creditCard.nom) {
                cardOwner.setText(creditCard.nom)
            }

            if (cardNumbers.text.toString() != creditCard.cardNumbers) {
                cardNumbers.setText(creditCard.cardNumbers)
            }

            if (cardExpirationDate.text.toString() != creditCard.expDate) {
                cardExpirationDate.setText(creditCard.expDate)
            }
         }
    }
}