package com.example.android.sharingfiles_review;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
DocumentsProvider復習用アクティビティ
 */
public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 0;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent.ACTION_OPEN_DOCUMENTでは、DocumentsProviderをinvokeし、
                //DocumentsProviderを実装するアクティビティからコンテンツが提供される
                //(システムにより作成されたUIで、各アクティビティのコンテンツが一覧表示される)
                //(TextOutputWithFileName参照)
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //MIMEタイプの指定は必須
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
        image = (ImageView)findViewById(R.id.image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Uri returnedUri = data.getData();
            image.setImageURI(returnedUri);
        }
    }
}
