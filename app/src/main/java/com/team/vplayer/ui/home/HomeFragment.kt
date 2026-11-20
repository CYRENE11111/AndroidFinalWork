package com.team.vplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.vplayer.databinding.FragmentHomeBinding

/**
 * 首页：本地视频列表。**成员 A 拥有**。
 *
 * TODO(成员A 第2次)：RecyclerView 网格/列表、卡片、排序、搜索、文件夹筛选。
 * 数据来自 ServiceLocator.videoRepository（成员 B）。
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
