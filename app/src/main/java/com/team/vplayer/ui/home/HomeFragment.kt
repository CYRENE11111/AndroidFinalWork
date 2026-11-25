package com.team.vplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.team.vplayer.R
import com.team.vplayer.data.model.Video
import com.team.vplayer.data.util.SortType
import com.team.vplayer.databinding.FragmentHomeBinding
import com.team.vplayer.ui.common.GridSpacingItemDecoration
import com.team.vplayer.ui.player.PlayerActivity
import kotlinx.coroutines.launch

/** 首页：本地视频列表。**成员 A 拥有**。 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: VideoAdapter
    private var currentList: List<Video> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = VideoAdapter(
            onClick = { video -> openPlayer(video) },
            onLongClick = { video -> viewModel.toggleFavorite(video); true },
        )
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(
            GridSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.space_s)),
        )

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query.orEmpty()); return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText.orEmpty()); return true
            }
        })

        binding.sortButton.setOnClickListener { anchor -> showSortMenu(anchor) }
        binding.columnButton.setOnClickListener { viewModel.toggleColumns() }
        binding.refreshButton.setOnClickListener { viewModel.refresh() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    currentList = state.videos
                    adapter.submitList(state.videos)
                    binding.emptyView.isVisible = state.videos.isEmpty()

                    (binding.recycler.layoutManager as? GridLayoutManager)?.let { lm ->
                        if (lm.spanCount != state.columns) lm.spanCount = state.columns
                    }
                    binding.columnButton.setImageResource(
                        if (state.columns == 1) R.drawable.ic_grid else R.drawable.ic_list,
                    )
                }
            }
        }
    }

    private fun showSortMenu(anchor: View) {
        val types = arrayOf(SortType.NAME, SortType.DURATION, SortType.SIZE, SortType.TIME)
        val labels = arrayOf(
            getString(R.string.sort_name),
            getString(R.string.sort_duration),
            getString(R.string.sort_size),
            getString(R.string.sort_time),
        )
        PopupMenu(requireContext(), anchor).apply {
            types.forEachIndexed { index, _ -> menu.add(0, index, index, labels[index]) }
            setOnMenuItemClickListener { item ->
                viewModel.setSortType(types[item.itemId])
                true
            }
            show()
        }
    }

    private fun openPlayer(video: Video) {
        val ids = ArrayList(currentList.map { it.id })
        val index = ids.indexOf(video.id).coerceAtLeast(0)
        startActivity(PlayerActivity.intent(requireContext(), ids, index))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}