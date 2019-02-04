* 概要
「SharingFiles」プロジェクトの復習用プロジェクト。
「SharingFiles」で学んだContentProviderの実装によるファイル共有を、
Android Developers「Sharing a file」https://developer.android.com/training/secure-file-sharing/share-file
記載の、多くの選択肢から選択する方法でやってみる。

* やること
- 表示するコンテンツの用意とURIの作成
- ListViewへの表示

** 構成
*** MainActivity
https://qiita.com/yamikoo@github/items/fe27482954cd73e55935
DocumtentProviderの復習用アクティビティ。
IntentにACTION_OPEN_DOCUMENTを指定し、setData()でmineタイプを指定して起動すると、
DocumentProviderが起動してシステム上にあるDocumentProviderで共有されているファイルが表示される。
戻ってきたIntentにはFileProviderと同じようにUriがデータとして含まれている。

*** ImagePickerActivity
- 画像をDocumentsProviderにより取得し、RecyclerViewでカード状に表示する
-- DocumentsProviderと取得したUriの処理
-- ファイルの作成・保存
-- RecyclerViewの作成
- RecyclerViewにイベントリスナを登録し、クリックされたアイテムの情報を取得する
- ActionBarの活用
- FileProviderの使用



・Intent.ACTION_OPEN_DOCUMENTとIntent.ACTION_PICKの違い
ACTION_OPEN_DOCUMENTでIntentを送ると、DocumentsProviderを実装したアクティビティのインスタンス一覧が独自UIで表示される。
ACTION_PICKでIntentを送ると、ACTION_PICKを受け入れるアクティビティ一覧から起動するアクティビティを選択する

