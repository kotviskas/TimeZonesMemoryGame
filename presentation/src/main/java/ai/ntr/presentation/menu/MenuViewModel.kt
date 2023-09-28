package ai.ntr.presentation.menu

import ai.ntr.presentation.util.EventChannel
import ai.ntr.presentation.util.GameMode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@ViewModelScoped
class MenuViewModel @Inject constructor() : ViewModel() {
    val startGame = EventChannel<GameMode>()

    fun setGameMode(mode: GameMode){
        startGame.send(mode, viewModelScope)
    }
}