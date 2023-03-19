package com.example.android.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.component.OnItemClickListener
import com.example.android.databinding.FavoriteFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavoriteFragmentBinding.inflate(
        inflater,
        container,
        false
    ).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter()

        binding.rvFavorites.adapter = adapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(v: View, position: Int) {
                    viewModel.toggle(v.tag as String)
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.list.collectLatest {
                adapter.submitList(it.toList())
            }
        }
    }
}