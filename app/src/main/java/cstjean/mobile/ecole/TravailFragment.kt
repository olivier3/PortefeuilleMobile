package cstjean.mobile.ecole

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cstjean.mobile.ecole.databinding.FragmentTravailBinding
import cstjean.mobile.ecole.travail.Travail
import kotlinx.coroutines.launch
import java.util.*

/**
 * Fragment pour la gestion d'un travail.
 *
 */
class TravailFragment : Fragment() {
    private var _binding: FragmentTravailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val args: TravailFragmentArgs by navArgs()
    private val travailViewModel: TravailViewModel by viewModels {
        TravailViewModelFactory(args.travailId)
    }

    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    /**
     * Lorsque la vue est créée.
     *
     * @param view La vue créée.
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cardOwner.doOnTextChanged { text, _, _, _ ->
                travailViewModel.updateTravail { oldTravail ->
                    oldTravail.copy(nom = text.toString())
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                travailViewModel.travail.collect { travail ->
                    travail?.let { updateUi(it) }
                }
            }
        }



        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            val newDateRemise =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE, Date::class.java) as Date
                else
                    bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            travailViewModel.updateTravail { it.copy(dateRemise = newDateRemise) }
        }
    }
    private fun updateUi(travail: Travail) {
        binding.apply {
// Pour éviter une loop infinie avec le update
            if (cardOwner.text.toString() != travail.nom) {
                cardOwner.setText(travail.nom)
            }
            card4digits.text = travail.dateRemise.toString()
            card4digits.setOnClickListener {
                findNavController().navigate(
                    TravailFragmentDirections.selectDateRemise(travail.dateRemise)
                )
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