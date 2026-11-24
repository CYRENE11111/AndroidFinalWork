# 播放模块（player）· 成员 C

## 职责
- ExoPlayer（Media3）封装、队列与连播
- 后台播放服务（通知栏、音频焦点）
- 倍速、字幕、定时关闭
- 画中画（PIP）与悬浮窗

## 关键类
| 类 | 说明 |
|---|---|
| `player/core/PlaybackController` | 播放控制接口（A 调用） |
| `player/core/PlayerManager` | 单例实现，暴露 `StateFlow<PlaybackState>` |
| `player/core/PlaybackState` | 播放状态快照 |
| `player/core/SleepTimer` | 定时关闭 |
| `player/service/PlaybackService` | 后台服务（MediaSessionService） |
| `player/subtitle/SubtitleManager` | 字幕扫描与加载 |
| `player/pip/PipManager` | 画中画入口 |
| `player/pip/FloatingWindow` | 悬浮窗 |

## 用法
- A 通过 `ServiceLocator.playback` 拿到 `PlayerManager`。
- `release()` 由 `PlaybackService` 销毁时调用；用户退出播放页（`isFinishing`）时界面也会调用它停止播放。

## 工作记录
> 每次工作结束追加一条，随代码一起提交（老师要看的 README）。

### 第 1 次
- 做了什么：
- 改了哪些文件：
- 遇到的问题：
- 下次从哪继续：
- 提交：