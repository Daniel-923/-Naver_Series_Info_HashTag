package com.android_proj1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android_proj1.Fragment.FragmentRecommend;
import com.android_proj1.Fragment.FragmentSearch;
import com.android_proj1.Fragment.FragmentTop100;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    TextView textView1;
    EditText editText;
    private DbOpenHelper dbOpenHelper;
    private WebNovelManager webNovelManager;
    private UserInput userInput;
    Button btnSelete, btnUpdate, btnDelete;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch();
    private FragmentRecommend fragmentRecommend = new FragmentRecommend();
    private FragmentTop100 fragmentTop100 = new FragmentTop100();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        textView1 = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.et_title);
        editText.setOnEditorActionListener(setTitle);
        btnSelete = (Button) findViewById(R.id.btn_selete);
        btnUpdate = (Button) findViewById(R.id.btn_update);*/
        final Bundle bundle = new Bundle();

        dbOpenHelper = new DbOpenHelper(this);

/*
        dbOpenHelper.open();
        dbOpenHelper.create();

        webNovelManager = new WebNovelManager(dbOpenHelper);


        String category = "판타지";
        String title = "재벌";
        
        //userInput = new UserInput("category", category);
        userInput = new UserInput("title", title);
        webNovelManager.service(WebNovelService.Name.SEARCH_READ, userInput);*/

/*        ServeThread thread = new ServeThread(dbOpenHelper);
        thread.start();*/

        if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

    }


    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.searchItem:
                    transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
                    break;
                case R.id.recommendItem:
                    transaction.replace(R.id.frameLayout, fragmentRecommend).commitAllowingStateLoss();
                    break;
                case R.id.top100Item:
                    transaction.replace(R.id.frameLayout, fragmentTop100).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }


    private class ServeThread extends Thread {
        private DbOpenHelper dbOpenHelper;

        public ServeThread(DbOpenHelper dbOpenHelper) {
            this.dbOpenHelper = dbOpenHelper;
        }

        @Override
        public void run() {
            super.run();

            dbOpenHelper.open();
            dbOpenHelper.create();

            webNovelManager = new WebNovelManager(dbOpenHelper);

            String category = "판타지";
            String title = "재벌";

            //userInput = new UserInput("title", title);

            userInput = new UserInput("category", category);
            webNovelManager.service(WebNovelService.Name.TOP100_CREATE, userInput);


            dbOpenHelper.runCSV();


        }
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            //textView.setText(bundle.getString("numbers"));                      //이런식으로 View를 메인 쓰레드에서 뿌려줘야한다.
        }
    };


    private TextView.OnEditorActionListener setTitle = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            // 텍스트 내용을 가져온다.
            String searchData = textView.getText().toString();


            // 텍스트 내용이 비어있다면...
            if (searchData.isEmpty()) {

                // 토스트 메세지를 띄우고, 창 내용을 비운다
                Toast.makeText(MainActivity.this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                textView.clearFocus();
                textView.setFocusable(false);
                textView.setFocusableInTouchMode(true);
                textView.setFocusable(true);

                return true;
            }

            // 텍스트 내용이 비어있지않다면
            switch (i) {

                // Search 버튼일경우
                case EditorInfo.IME_ACTION_SEARCH:

                    String search_title = editText.getText().toString();
                    textView1.setText(search_title);

                    String title = "title";

                    userInput = new UserInput(title, search_title);

                    // top100 요청
                    webNovelManager.service(WebNovelService.Name.SEARCH_CREATE, userInput);

                    break;

                // Enter 버튼일경우
                default:

                    // TODO : Write Your Code

                    return false;
            }

            // 내용 비우고 다시 이벤트 할수있게 선택
            textView.clearFocus();
            textView.setFocusable(false);
            textView.setText("");
            textView.setFocusableInTouchMode(true);
            textView.setFocusable(true);

            return true;

        }
    };


}