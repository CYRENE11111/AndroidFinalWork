# 每日工作脚本（每人 10 次）

三个文件夹对应三个人：

| 文件夹 | 成员 | 分支 |
|---|---|---|
| `scripts/A` | 界面 | `feat/a-ui` |
| `scripts/B` | 数据 | `feat/b-data` |
| `scripts/C` | 播放内核 | `feat/c-player` |

## 前提

- **把整个 `scripts` 文件夹放到工程根目录**（和 `app`、`gradle`、`docs` 同级）。
  放对位置后，脚本能自动认出工程路径。
- 组员各自只跑自己文件夹里的 10 个：A 跑 `A/01..10`，B 跑 `B/01..10`，C 跑 `C/01..10`。
- 不需要安装任何东西，PowerShell 是 Windows 自带的。

## 怎么用

1. 先 `git pull`，切到自己的分支：`git checkout feat/a-ui`（B/C 同理）。
2. 每天**双击**对应的 `01.bat`、`02.bat`……（最省事，已处理执行策略）。
   也可以右键 `.ps1` → 使用 PowerShell 运行。
   - 脚本会**先确认工程路径**（默认本仓库根目录，直接回车即可），并校验：
     - 路径下必须有 `app/src/main/java/com/team/vplayer`
     - 如果 `.git` 存在，会核对仓库地址是不是 `https://github.com/CYRENE11111/AndroidFinalWork.git`
   - 校验不通过会直接退出，不会乱写。
3. 脚本写完文件后，用 Android Studio 打开工程 → Gradle Sync → 编译运行。
4. 通过后提交：
   ```bash
   git add .
   git commit -m "feat(界面): 第 01 次工作"
   git push
   ```

## 注意

- **只有最终全部跑完才一定编译通过**（中间某几次可能编译不过，因为后面的文件还没加），这是正常的。
- 每人按顺序跑 01 → 10，一天一次，正好 10 天 10 次提交。
- 三人可以并行：A 只写 `ui/` 和 `res/`、`common/`、`AndroidManifest.xml`；B 只写 `data/`；C 只写 `player/`。
- 跑完自己 10 次后，请 A 把三条分支合并到 `main`。

## 文件去哪了

脚本把代码写进 `app/src/main/java/com/team/vplayer/...` 和 `app/src/main/res/...`，
每次运行会在 `scripts/` 同级的工程里生成/覆盖对应文件，并在终端列出写了哪些文件。
