package cstjean.mobile.portefeuille

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.portefeuille.databinding.FragmentCardsListBinding

class CreditCardsListFragment : Fragment() {
    private var _binding: FragmentCardsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

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

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}