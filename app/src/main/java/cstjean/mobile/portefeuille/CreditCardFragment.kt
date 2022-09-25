package cstjean.mobile.portefeuille

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cstjean.mobile.portefeuille.databinding.FragmentCreditCardBinding

class CreditCardFragment : Fragment() {
    private var _binding: FragmentCreditCardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditCardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}