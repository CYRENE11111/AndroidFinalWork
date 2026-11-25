# 数据模块（data）· 成员 B

## 职责
- 本地视频扫描（MediaStore）、按文件夹分组
- Room 数据库、`VideoRepository` 实现
- 历史记录、收藏、播放进度、偏好（SharedPreferences）
- 播放队列持久化、桌面小组件 / 通知栏数据

## 关键类
| 类 | 说明 |
|---|---|
| `data/VideoRepository` | 数据层接口（A/C 调用） |
| `data/repository/VideoRepositoryImpl` | 默认实现 |
| `data/DataManager` | 初始化入口（A 在 App 调用） |
| `data/db/VideoDatabase` | Room 数据库 |
| `data/db/*Dao` | 视频 / 历史 / 收藏 / 队列 / 进度 DAO |
| `data/scanner/VideoScanner` | MediaStore 扫描 |
| `data/prefs/Prefs` | 偏好存储 |
| `data/util/VideoSorter` | 列表排序 |
| `data/model/*` | `Video` / `Folder` / `HistoryItem` / `Progress` / `Favorite` / `QueueItem` |

## 用法
- 实现类提供后，`ServiceLocator.registerVideoRepository(impl)` 注册。
- 列表用 `Flow` 暴露：`videoRepository.videos` / `.history`。

## 工作记录
> 每次工作结束追加一条，随代码一起提交（老师要看的 README）。

### 第 1 次
- 做了什么：
- 改了哪些文件：
- 遇到的问题：
- 下次从哪继续：
- 提交：