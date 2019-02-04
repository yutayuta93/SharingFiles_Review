package com.example.android.sharingfiles_review;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ImagePickerActivity extends AppCompatActivity implements MyViewAdapter
        .OnItemSelectedListener{
    File imagePath;
    boolean isSelected;
    RecyclerView view;
    ListItem itemSelected;
    View viewSelected;
    Toolbar toolbar;
    final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_select);

        //ToolBarの登録
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        初期化処理
        画像を保存・共有するディレクトリがなければ作成して、そのFileオブジェクトを取得する
         */
        imagePath = initialize();

        view = (RecyclerView)findViewById(R.id.rv);
        createRecyclerViewFromDirectory();

        /*
        新規画像の取得処理
        ボタンクリックでDocumentsProviderを開くインテントを飛ばす
         */
        Button button = (Button) findViewById(R.id.btn_select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        /*仮のボタン

        Button button_reset = (Button)findViewById(R.id.reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noSelect();
                invalidateOptionsMenu();
            }
        });
        */
    }

    private File initialize(){
        //初期化処理
        //画像保存・共有用のフォルダがなければ作成
        File imagePath = new File(getFilesDir(),"images");
        if(!imagePath.isDirectory()){
            imagePath.mkdir();
        }
        return imagePath;
    }

    /*
    DocumentsProviderで取得した画像をimagePathに保存
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode ==RESULT_OK){
            Uri resultUri = data.getData();
            Cursor returnedCursor = getContentResolver().query(resultUri,null,null,null,null);
            returnedCursor.moveToFirst();
            int nameIndex = returnedCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            String fileName = returnedCursor.getString(nameIndex);

            File newFile = new File(imagePath,fileName);
            FileOutputStream fos;
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                fos = new FileOutputStream(newFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            createRecyclerViewFromDirectory();
        }
    }

    /*
    filePathにある画像一覧を取得し、RecyclerViewに表示する
     */
    public void createRecyclerViewFromDirectory(){
        File[] imageFiles = imagePath.listFiles();
        if(imageFiles.length>=1){
            ArrayList<ListItem> data = new ArrayList<>();
            for(int i = 0;i<imageFiles.length;i++){
                ListItem item = new ListItem();
                File image = imageFiles[i];
                item.setFile(image);
                item.setUri(Uri.fromFile(image));
                item.setTitle(image.getName());
                item.setImage(BitmapFactory.decodeFile(image.getAbsolutePath()));
                data.add(item);
            }
            MyViewAdapter adapter = new MyViewAdapter(data,ImagePickerActivity.this);

            GridLayoutManager manager = new GridLayoutManager(ImagePickerActivity.this,2);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            view.setAdapter(adapter);
            view.setLayoutManager(manager);
        }
    }


    /**
     * RecyclerView上でViewが選択された時に呼び出される。
     * MyViewAdapterから選択されたビュー(CardView)とListItemが返され、それぞれクラス変数viewSelected、ItemSelectedに設定される。
     * 同時に、AppBarのレイアウトを選択状態のレイアウトに変更する
     *
     * @param v 選択されたView(CardView)
     * @param item 選択されたViewのデータ
     */
    @Override
    public void onItemSelected(View v,ListItem item) {
        if(isSelected){
            noSelect();
        }
        itemSelected = item;
        viewSelected = v;
        v.setAlpha(0.3f);
        isSelected = true;
        invalidateOptionsMenu();
    }

    public void noSelect(){
        if(viewSelected != null) {
            viewSelected.setAlpha(1);
            viewSelected = null;
            itemSelected = null;
            isSelected = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isSelected){
            getMenuInflater().inflate(R.menu.menu_selected,menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_unselected,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        File selectedFile = itemSelected.getFile();
        switch(item.getItemId()){
            case R.id.delete:
                selectedFile.delete();
                createRecyclerViewFromDirectory();
                return true;
            case R.id.select:
                onItemDecided(selectedFile);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void onItemDecided(File selectedFile) {
        Intent intent = getIntent();
        String str = selectedFile.toString();
        Log.d("FILE",str);
        Uri fileUri = FileProvider.getUriForFile(this,"com.example.android.sharingfiles_review.fileprovider",selectedFile);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(fileUri,getContentResolver().getType(fileUri));
        setResult(RESULT_OK,intent);
        finish();

    }
}
