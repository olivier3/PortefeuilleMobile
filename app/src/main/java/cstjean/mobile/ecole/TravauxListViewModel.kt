package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.travail.Travail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pour la liste des travaux.
 *
 * @property travaux La liste des travaux.
 *
 */
class TravauxListViewModel : ViewModel() {
    private val travailRepository = TravailRepository.get()

    private val _travaux: MutableStateFlow<List<Travail>> = MutableStateFlow(emptyList())
    val travaux: StateFlow<List<Travail>>
        get() = _travaux.asStateFlow()

    init {
        viewModelScope.launch {
            travailRepository.getTravaux().collect {
                _travaux.value = it
            }
        }
    }

    suspend fun addTravail(travail: Travail) {
        travailRepository.addTravail(travail)
    }
}