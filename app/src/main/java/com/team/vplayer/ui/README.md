# 界面模块（ui）· 成员 A

## 职责
- 首页视频列表、播放页、播放列表、历史/收藏、设置、画中画/悬浮/小组件界面
- 底部导航与页面跳转、主题与夜间模式
- 共享文件：`res/values`（colors/dimens/themes/ui_strings）、`res/layout`、`res/drawable`、`AndroidManifest.xml`、`build.gradle*`

## 关键类
| 类 | 说明 |
|---|---|
| `ui/MainActivity` | 主界面，底部导航（首页/历史/设置） |
| `ui/home/HomeFragment` | 首页视频列表 |
| `ui/player/PlayerActivity` | 播放页 |
| `ui/playlist/PlayListFragment` | 播放列表 |
| `ui/history/HistoryFragment` | 历史 / 收藏 |
| `ui/settings/SettingsFragment` | 设置 |
| `common/ServiceLocator` | 依赖注入入口 |

## 用法
- 取数据：`ServiceLocator.videoRepository`（成员 B 提供）
- 取播放器：`ServiceLocator.playback`（成员 C 的 `PlayerManager`）
- 播放页 `onDestroy` 只解绑（`playerView.player = null`），**不调 `release`**

## 工作记录
> 每次工作结束追加一条，随代码一起提交（老师要看的 README）。

### 第 1 次
- 做了什么：
- 改了哪些文件：
- 遇到的问题：
- 下次从哪继续：
- 提交：
