package com.android_proj1.Search;

import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelManager;

public class SearchRead extends SearchNovel {
    private DbOpenHelper dbOpenHelper;

    public SearchRead(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        String title = getTitle();
        int indexNun = 0;

        dbOpenHelper = getDbOpenHelper();

        Cursor isTitle = dbOpenHelper.selectTitle(title);
        Cursor cursor = null;

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (isTitle.getCount() == 0) {
                    WebNovelManager webNovelManager = new WebNovelManager(dbOpenHelper);
                    webNovelManager.service(Name.SEARCH_CREATE, userInput);
                    Log.e("Tag", "isTitle.getCount == 0: SearchNovel 103");
                }
            }
        }).start();

        try {
            Thread.sleep(2000);

            cursor = dbOpenHelper.selectSearch(title);

            if (cursor.getCount() == 0) {
                Log.e("Tag", "cursor.getCount == 0: SearchNovel 113");
            }

            cursor.moveToFirst();
            Log.e("Tag", "title : " + cursor.getCount());

            do {
                indexNun++;
                Log.d("Tag", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"); //로그캣 출력
                Log.d("Tag", "title : " + indexNun + " " + cursor.getString(0));
                Log.d("Tag", "link : " + indexNun + " " + cursor.getString(1));
                Log.d("Tag", "summary : " + indexNun + " " + cursor.getString(2));
                Log.d("Tag", "score : " + indexNun + " " + cursor.getString(3));
                Log.d("Tag", "author : " + indexNun + " " + cursor.getString(4));
                Log.d("Tag", "img : " + indexNun + " " + cursor.getString(5));
                Log.d("Tag", "category : " + indexNun + " " + cursor.getString(7));
            } while (cursor.moveToNext());

            cursor.close();
            dbOpenHelper.close();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Name name() {
        return Name.SEARCH_READ;
    }
}
