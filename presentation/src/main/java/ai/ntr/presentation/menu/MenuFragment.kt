package ai.ntr.presentation.menu

import ai.ntr.presentation.databinding.FragmentMenuBinding
import ai.ntr.presentation.util.GameMode
import ai.ntr.presentation.util.observeWithLifecycle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlay.setOnClickListener {
            binding.clStartGame.visibility = View.GONE
            binding.clChooseMode.visibility = View.VISIBLE
        }
        binding.btnPlayCasual.setOnClickListener {
            viewModel.setGameMode(GameMode.casual)
        }
        binding.btnPlayTime.setOnClickListener {
            viewModel.setGameMode(GameMode.time)
        }
        viewModel.startGame.flow().observeWithLifecycle(this){
            val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(it)
            findNavController().navigate(action)
        }
    }
}