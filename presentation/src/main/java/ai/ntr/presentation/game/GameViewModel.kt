package ai.ntr.presentation.game

import ai.ntr.domain.entity.City
import ai.ntr.domain.usecase.GetCitiesUseCase
import ai.ntr.domain.usecase.ShuffleCitiesUseCase
import ai.ntr.presentation.util.EventChannel
import ai.ntr.presentation.util.GameMode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.w3c.dom.Text
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    getCitiesUseCase: GetCitiesUseCase,
    shuffleCitiesUseCase: ShuffleCitiesUseCase
) :
    ViewModel() {
    val setCities = EventChannel<List<City>>()
    val setQuestion = EventChannel<City>()
    val finishGame = EventChannel<String>()
    val setTimer = EventChannel()

    private var gameMode = GameMode.casual
    private var numberQuestion = 1
    private val countQuestion = 6
    private lateinit var cities: List<City>
    private var score: Int = 0

    init {
        getCitiesUseCase.invoke().onSuccess {
            setCities.send(it, viewModelScope)
        }
        shuffleCitiesUseCase.invoke().onSuccess {
            cities = it
        }
    }

    fun getQuestion() {
        if (gameMode == GameMode.time) {
            setTimer.send(Unit, viewModelScope)
        }

        if (numberQuestion <= countQuestion) {
            setQuestion.send(cities[numberQuestion - 1], viewModelScope)
        } else {
            val textScore = "$score/$countQuestion"
            finishGame.send(textScore, viewModelScope)
        }
    }

    fun saveAnswer(answer: String?) {
        if (answer == cities[numberQuestion - 1].name) {
            score++
        }
        numberQuestion++
    }

    fun setGameMode(gameMode: GameMode) {
        this.gameMode = gameMode
    }
}