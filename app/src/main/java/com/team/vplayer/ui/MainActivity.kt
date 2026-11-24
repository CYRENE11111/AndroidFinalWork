package com.team.vplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.team.vplayer.R
import com.team.vplayer.databinding.ActivityMainBinding
import com.team.vplayer.ui.history.HistoryFragment
import com.team.vplayer.ui.home.HomeFragment
import com.team.vplayer.ui.settings.SettingsFragment

/**
 * 主界面：底部导航（首页 / 历史 / 设置）。**成员 A 拥有**。
 *
 * TODO(成员A 第2-9次)：完善各页面与播放页跳转。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_history -> HistoryFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> return@setOnItemSelectedListener false
            }
            showFragment(fragment)
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, fragment)
            .commit()
    }
}
