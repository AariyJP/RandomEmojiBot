# RandomEmojiBot

**※不具合を防止するため、ボットには管理者権限を付与しておいてください。**

# 起動方法

**あらかじめJava17以降をインストールしておいてください。**

**https://www.oracle.com/jp/java/technologies/downloads/#java17**

**https://discord.com/developers のボットページで特権インテント（Privileged Gateway Intent）の3つのスイッチをONにしてください。**

Windowsの方はrun.bat、Linuxの方はrun.sh（要実行権限）を以下の通り編集してから実行してください。

元のファイル→`java -jar RandomChatBot.jar token`

ファイル内の文字列を以下のように置き換えてください。

token→ボットトークン

### 機能一覧

`/わびスロット` → 3つの絵文字をランダムに表示します。（一日二回まで）

`/reset` → （管理者向け）スロットの回数制限をリセットします。

日付が変わる時に自動でスロットの回数記録をリセットします。