package com.pablogallardo.rickmortyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pablogallardo.rickmortyapp.databinding.ActivityMainBinding
import com.pablogallardo.rickmortyapp.helpers.extensions.handleError
import com.pablogallardo.rickmortyapp.helpers.extensions.isNetworkAvailable
import com.pablogallardo.rickmortyapp.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        initObservers()
        initListeners()

        viewModel.loadCharacters(isNetworkAvailable)
        initAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.isLoading }
                .distinctUntilChanged()
                .collect {
                    binding.characterList.isVisible = !it
                    showShimmerEffect(it)
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.items }
                .distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty())
                        (binding.characterList.adapter as? CharactersAdapter)?.submitList(it)
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.error }
                .collect {
                    handleError(it)
                }
        }
    }

    private fun initListeners() {
        binding.buttonRefresh.setOnClickListener {
            viewModel.loadCharacters(isNetworkAvailable)
        }

        binding.characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val listSize = (recyclerView.adapter as CharactersAdapter).currentList.size

                if (layoutManager.findLastVisibleItemPosition() >= listSize - 2)
                    viewModel.notifyLastVisible()
            }
        })
    }

    private fun initAdapter() {
        val adapter = CharactersAdapter { clickedCharacter ->
            goToDetail(clickedCharacter.id)
        }
        adapter.setHasStableIds(true)
        binding.characterList.layoutManager = LinearLayoutManager(this)
        binding.characterList.adapter = adapter
    }

    private fun goToDetail(id: Int) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.INTENT_ID, id)
        })
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding.characterList.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            binding.characterList.visibility = View.VISIBLE
        }
    }
}
