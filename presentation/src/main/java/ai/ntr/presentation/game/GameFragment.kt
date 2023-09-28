package ai.ntr.presentation.game

import ai.ntr.presentation.R
import ai.ntr.presentation.databinding.FragmentGameBinding
import ai.ntr.presentation.util.observeWithLifecycle
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        arguments?.let {
            viewModel.setGameMode(GameFragmentArgs.fromBundle(it).gameMode)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCities.flow().observeWithLifecycle(this) {
            binding.apply {
                city1.imageView.setImageDrawable(setClockImage(it[0].time))
                city1.textView.text = it[0].name
                city2.imageView.setImageDrawable(setClockImage(it[1].time))
                city2.textView.text = it[1].name
                city3.imageView.setImageDrawable(setClockImage(it[2].time))
                city3.textView.text = it[2].name
                city4.imageView.setImageDrawable(setClockImage(it[3].time))
                city4.textView.text = it[3].name
                city5.imageView.setImageDrawable(setClockImage(it[4].time))
                city5.textView.text = it[4].name
                city6.imageView.setImageDrawable(setClockImage(it[5].time))
                city6.textView.text = it[5].name
            }
        }

        viewModel.setQuestion.flow().observeWithLifecycle(this) {
            binding.cityAnswer.imageView.setImageDrawable(setClockImage(it.time))
        }

        viewModel.setTimer.flow().observeWithLifecycle(this) {
            binding.tvTimer.visibility = View.VISIBLE
            startTimer()
        }

        viewModel.finishGame.flow().observeWithLifecycle(this) {
            timer?.cancel()
            binding.clAnswer.visibility = View.GONE
            binding.tvTimer.visibility = View.GONE
            binding.tvScore.visibility = View.VISIBLE
            binding.tvScore.text = it
        }

        binding.btnGoAnswer.setOnClickListener {
            binding.apply {
                viewModel.getQuestion()
                table.visibility = View.GONE
                btnGoAnswer.visibility = View.GONE
                clAnswer.visibility = View.VISIBLE
            }
        }

        binding.btnAnswer.setOnClickListener {
            setAnswer()
        }
    }

    private fun startTimer(){
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {
                setAnswer()
            }
        }
        timer?.start()
    }

    private fun setAnswer(){
        timer?.cancel()
        viewModel.saveAnswer(binding.etAnswer.text.toString())
        binding.etAnswer.text?.clear()
        viewModel.getQuestion()
    }

    private fun setClockImage(time: Int): Drawable? {
        var drawable: Drawable? = ResourcesCompat.getDrawable(resources,R.drawable.clock11, null)!!
        when (time) {
            12 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock12,null)
            11 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock11, null)
            10 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock10, null)
            9 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock9, null)
            5 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock5, null)
            6 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock6, null)
            7 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock7, null)
            8 -> drawable = ResourcesCompat.getDrawable(resources,R.drawable.clock8, null)
        }
        return drawable
    }

}