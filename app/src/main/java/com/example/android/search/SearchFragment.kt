package com.example.android.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.android.R
import com.example.android.component.OnItemClickListener
import com.example.android.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment(), MenuProvider {

    private lateinit var binding: SearchFragmentBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SearchFragmentBinding.inflate(
        inflater,
        container,
        false
    ).also {
        binding = it

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchAdapter()

        binding.rvSearch.adapter = adapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(v: View, position: Int) {
                    viewModel.toggle(v.tag as String) {
                        notifyItemChanged(position)
                    }
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.result.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_fragment, menu)

        val menuItem = menu.findItem(R.id.action_searchbar)

        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "검색어 입력"
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }
}