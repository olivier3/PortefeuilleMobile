package cstjean.mobile.portefeuille

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.databinding.FragmentCardsListBinding
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.launch
import java.util.*

/**
 * Fragment pour la liste des cartes de crédit.
 *
 * @author Olivier Bilodeau et Laura Tram
 */
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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_cards_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.new_card -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val faker = Faker()

                            val newCreditCard = creditCardsListViewModel.createCreditCard(faker)
                            creditCardsListViewModel.addCreditCard(newCreditCard)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}