package com.team.vplayer.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.vplayer.databinding.FragmentHistoryBinding

/**
 * 历史 / 收藏页。**成员 A 拥有**。
 *
 * TODO(成员A 第7次)：历史续播、收藏、长按多选删除。
 * 数据来自 ServiceLocator.videoRepository（成员 B）。
 */
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
