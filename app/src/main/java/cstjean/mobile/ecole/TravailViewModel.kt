package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.travail.Travail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class TravailViewModel(travailId: UUID) : ViewModel() {
    private val travailRepository = TravailRepository.get()
    private val _travail: MutableStateFlow<Travail?> = MutableStateFlow(null)
    val travail: StateFlow<Travail?>
        get() = _travail.asStateFlow()
    init {
        viewModelScope.launch {
            _travail.value = travailRepository.getTravail(travailId)
        }
    }

    fun updateTravail(onUpdate: (Travail) -> Travail) {
        _travail.update { oldTravail ->
            oldTravail?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        travail.value?.let { travailRepository.updateTravail(it) }
    }
}

class TravailViewModelFactory(private val travailId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TravailViewModel(travailId) as T
    }
}