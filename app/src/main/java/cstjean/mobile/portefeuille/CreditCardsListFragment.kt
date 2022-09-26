package cstjean.mobile.portefeuille

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.portefeuille.databinding.FragmentCardsListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CreditCardsListFragment : Fragment() {
    private var _binding: FragmentCardsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val creditCardsListViewModel : CreditCardsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsListBinding.inflate(layoutInflater, container, false)

        binding.creditcardsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Supprimer la création de l'adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                creditCardsListViewModel.creditCards.collect { creditCards ->
                    binding.creditcardsRecyclerView.adapter = CreditCardsListAdapter(creditCards) { creditCardId ->
                        findNavController().navigate(
                            CreditCardsListFragmentDirections.showCreditCardDetail(creditCardId))
                    }
                }
            }
        }
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}