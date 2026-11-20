# VPlayer · 本地视频播放器（三人课程项目）

> 本文档是项目总纲：技术选型、目录归属、共享规则、接口契约、Git 规范、模块 README 与工作记录。
> 三份个人工作计划见 `docs/` 目录。

---

## 1. 项目简介

模仿 MX Player / VLC / nPlayer 的**本地视频播放器**：只播放设备里已经存在的视频文件，不含在线内容源。
理由：课程项目需要能完全闭环、又能体现技术含量的题目；本地播放器一部手机就能测，不需要后端。

**应用名**：VPlayer（暂定，可改）
**包名**：`com.team.vplayer`（暂定，可改）

---

## 2. 技术栈

| 用途 | 选择 |
|---|---|
| 语言 | Kotlin |
| 最低版本 | minSdk 24（Android 7.0），targetSdk 34 |
| 播放内核 | AndroidX Media3 ExoPlayer |
| 数据持久化 | Room + Kotlin Coroutines / Flow |
| 图片加载 | Coil（缩略图） |
| UI | ViewBinding + Material Components + RecyclerView |
| 架构 | MVVM（每模块一个 Repository + ViewModel） |
| 依赖注入 | 手写 `ServiceLocator`（不引入 Hilt/Koin） |

> 以上为**我的建议**，如果课程老师对某些库有要求（例如要求用 Java / MediaPlayer），以课程要求为准。

---

## 3. 功能清单（完整版）

**核心**
- 本地视频扫描（MediaStore）、按文件夹分组
- 视频列表（网格/列表两种视图）、搜索、排序（名称/时长/大小/时间）
- 播放页：播放/暂停、进度拖动、上/下一首、全屏
- 历史记录（续播）、收藏
- 播放队列与自动连播

**交互增强**
- 手势：左侧滑调亮度、右侧滑调音量、横向滑快进/快退
- 倍速（0.5–3.0）、音调
- 字幕（SRT/ASS）：选择、样式、同步
- 锁定模式（防误触）
- 后台播放（通知栏控制、音频焦点）
- 定时关闭（15/30/60 分钟、播完停止）

**高级**
- 画中画（PIP）
- 悬浮窗播放（浮窗权限）
- 桌面小组件（最近播放）
- 夜间模式

---

## 4. 目录与归属（核心规则）

每个人的**工作目录**如下。只能在标明的目录内新建、修改、删除、移动文件。

| 路径 | 负责人 | 说明 |
|---|---|---|
| `app/src/main/java/com/team/vplayer/ui/**` | **A** | 全部界面（首页/播放页/列表/历史/设置） |
| `app/src/main/java/com/team/vplayer/ui/README.md` | **A** | 界面模块说明 + 工作记录 |
| `app/src/main/java/com/team/vplayer/data/**` | **B** | 数据层（实体/数据库/仓库/扫描/偏好） |
| `app/src/main/java/com/team/vplayer/data/README.md` | **B** | 数据模块说明 + 工作记录 |
| `app/src/main/java/com/team/vplayer/player/**` | **C** | 播放内核（ExoPlayer 封装/服务/字幕/PIP） |
| `app/src/main/java/com/team/vplayer/player/README.md` | **C** | 播放模块说明 + 工作记录 |
| `app/src/main/java/com/team/vplayer/common/**` | **A（共享）** | 公共工具、扩展函数、常量、`ServiceLocator` |
| `app/src/main/res/values/colors.xml`、`dimens.xml`、`themes.xml`、`ui_strings.xml` | **A** | 主题、配色、度量、界面文案 |
| `app/src/main/res/values/data_strings.xml` | **B** | 数据相关文案 |
| `app/src/main/res/values/player_strings.xml` | **C** | 播放相关文案 |
| `app/src/main/res/values-night/**` | **A** | 夜间模式资源 |
| `app/src/main/res/xml/**` | **A** | 设置页 / 小组件配置等 XML |
| `app/src/main/res/layout/**` | **A** | 所有布局文件 |
| `app/src/main/res/drawable/**` | **A** | 图标、背景、形状 |
| `app/src/main/AndroidManifest.xml` | **A（共享）** | 统一注册权限/组件 |
| `app/build.gradle.kts`、`build.gradle.kts`、`settings.gradle.kts`、`gradle/libs.versions.toml` | **A（共享）** | 依赖与构建配置 |
| `docs/**`（根目录） | 各自 | 只建自己的文件；共享文档（本 README）由 A 维护 |

> 字符串按模块拆成三个文件，是为了避免三人同时改 `strings.xml` 产生冲突。

---

## 5. 共享文件规则（避免冲突）

1. **共享文件只有负责人能改**：`common/`、共享 `res/values`、`AndroidManifest.xml`、`build.gradle*` 由 **A** 统一修改。
2. **需要改共享文件时走"需求清单"**：把要加的内容（依赖坐标、权限名、字符串 key、要注册的 Activity/Service）写进模块 README 的工作记录，并在群里提出；A 在**当次工作结束前**统一处理。
3. **允许 import，不允许改**：可以 `import` 别人目录里的接口和模型（如 `data.model.Video`），但绝不改它们的文件。
4. **第 1 次工作结束时**，A 必须把 B/C 需要的依赖和权限全部加好，否则后面会卡住。

---

## 6. 接口契约（第 1 次工作定稿，之后只改自己那份）

### 6.1 `VideoRepository`（B 拥有，A/C 调用）

```kotlin
// app/src/main/java/com/team/vplayer/data/VideoRepository.kt
interface VideoRepository {
    // 列表（Flow：Room 天然支持，A 的 ViewModel 直接收集）
    val videos: Flow<List<Video>>
    val folders: Flow<List<Folder>>
    suspend fun search(query: String): List<Video>
    suspend fun refresh()                       // 触发一次重新扫描
    // 历史 / 收藏
    val history: Flow<List<HistoryItem>>
    suspend fun clearHistory()
    suspend fun removeHistory(id: Long)
    suspend fun isFavorite(id: Long): Boolean
    suspend fun toggleFavorite(id: Long)
    // 进度
    suspend fun saveProgress(id: Long, positionMs: Long, durationMs: Long)
    suspend fun getProgress(id: Long): Progress?
    // 偏好（倍速/字幕/解码/主题等，读写 SharedPreferences）
    var playbackSpeed: Float
    var autoPlayNext: Boolean
    var subtitleEnabled: Boolean
    var hardwareDecode: Boolean
}
```

### 6.2 `PlaybackController`（C 拥有，A 调用）

```kotlin
// app/src/main/java/com/team/vplayer/player/PlaybackController.kt
interface PlaybackController {
    val state: StateFlow<PlaybackState>   // 播放/暂停/缓冲/结束 + 当前位置
    fun setItems(videos: List<Video>, startIndex: Int)
    fun play()
    fun pause()
    fun playPause()
    fun seekTo(positionMs: Long)
    fun seekBy(deltaMs: Long)             // 手势快进/快退
    fun next()
    fun previous()
    fun setSpeed(speed: Float)
    fun setRepeatMode(mode: RepeatMode)
    fun selectSubtitleTrack(index: Int)
    fun release()
    // PIP / 悬浮
    fun enterPip(activity: Activity)
    fun showFloatingWindow(activity: Activity)
}
```

> **`release()` 的约定**：A 的播放页 `onDestroy` **只解绑**（`playerView.player = null`），**不调 `release`**；
> 播放器由 C 的 `PlaybackService` 持有，退出页面后后台继续播；`release` 由 Service 销毁时调用。

### 6.3 依赖注入（手写 ServiceLocator）

不引入 Hilt/Koin，避免课程项目被构建配置卡住。

```kotlin
// app/src/main/java/com/team/vplayer/common/ServiceLocator.kt（A 拥有）
object ServiceLocator {
    @Volatile var videoRepository: VideoRepository? = null
    val playback: PlaybackController get() = PlayerManager   // C 的单例
}
```

- C 的 `PlayerManager` 是单例，A 直接 `import` 使用。
- B 提供 `VideoRepository` 实现，并在 `App.onCreate`（A 写）里完成注册；或 B 提供 `DataManager.init(context)` 由 A 调用。
- 三人只依赖接口，不互相 new 实现类。

### 6.4 共享模型（B 拥有，A/C 可读）

```kotlin
// app/src/main/java/com/team/vplayer/data/model/Video.kt
data class Video(
    val id: Long,
    val path: String,
    val title: String,
    val durationMs: Long,
    val sizeBytes: Long,
    val width: Int,
    val height: Int,
    val folderName: String,
    val addedAt: Long,
)
```

---

## 7. Git 与提交规范（对应"每人 10 天 10 次提交"）

- 仓库分支：`main`（整合）+ `feat/a-ui` / `feat/b-data` / `feat/c-player`（各自开发）。
- **每人 10 次工作 = 10 次提交，分 10 天完成**（大约每天 1 次）。
- **每次提交 = 本次的功能代码 + 对应模块 README 的更新**（在 README 的"工作记录"章节追加一条）。
- **每次提交必须包含一定行数的真实代码**，不能只提交文档或空提交——老师会看代码行数。
- commit message 用约定式前缀，例如：
  - `feat(列表): 完成首页网格与排序`
  - `fix(播放): 修复拖动进度条跳帧`
  - `docs(界面): 追加第 3 次工作记录`
- **合并**：只有 A 把三条分支合并到 `main`；B/C 不合并，只在最后请 A 合并。
- 最终 `main` 上应能看到三人各约 10 次、共约 30 次提交。

---

## 8. 模块 README 与工作记录

每人维护**自己模块的 README**（也就是提交时要交的那份）：

| 文件 | 负责人 |
|---|---|
| `app/src/main/java/com/team/vplayer/ui/README.md` | A |
| `app/src/main/java/com/team/vplayer/data/README.md` | B |
| `app/src/main/java/com/team/vplayer/player/README.md` | C |

内容：模块职责、关键类、用法说明，最后留一个 **工作记录** 章节。每次工作结束追加一条：

```markdown
### 第 NN 次（日期）
- 做了什么：
- 改了哪些文件：
- 遇到的问题：
- 下次从哪继续：
- 提交：分支 / commit 短哈希 / 说明
```

> 作用：**下次继续时不用重新想**；同时也是老师检查"每天有进展"的证据，随代码一起提交。
> `docs/` 下的计划文档由 A 维护，可按需提交，不计入 10 次功能提交。

---

## 9. 里程碑（与三人计划对齐）

| 时点 | 目标 |
|---|---|
| 第 1 次后 | 契约、目录规范、依赖与权限到位，App 能编译 |
| 第 3 次后 | 首页列表 + 播放页骨架（先接假数据） |
| 第 5 次后 | 真数据列表 + 基础播放控制 + 手势 |
| 第 7 次后 | 历史/收藏 + 队列连播闭环 |
| 第 8 次后 | 设置 + 高级功能（PIP/悬浮/小组件/定时）完成 |
| 第 9–10 次 | 三人联调、打磨、演示、合并 `main` |

---

## 10. 风险与注意事项

- **共享文件冲突**：`AndroidManifest.xml`、`build.gradle`、共享 `res/values` 是冲突高发区，一律走"需求清单"。
- **进度不一致**：每 2–3 次工作开一次 10 分钟同步会，对齐接口。
- **提交行数**：老师要求每次提交有代码量，所以别把工作记录单独提交，一定和代码一起。
- **模拟器解码差**：务必用真机测试播放效果，尤其是字幕和倍速。
- **ExoPlayer/Media3 版本**：三人统一用一个版本，由 A 写在 `libs.versions.toml`。
- **权限适配**：Android 13+ 用 `READ_MEDIA_VIDEO`，低版本用 `READ_EXTERNAL_STORAGE`；悬浮窗和 PIP 需要额外权限。
