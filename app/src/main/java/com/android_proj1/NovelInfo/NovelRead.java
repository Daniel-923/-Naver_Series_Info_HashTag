package com.android_proj1.NovelInfo;

import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelManager;

// NovelRead를 통해 소설의 정보를 본다는 것은 이미 Top100이나 Search를 통해 db에 등록된 소설을 읽겠다는 뜻이다.
// 따라서 이 소설은 항상 db에 있는 소설만 가져오는 클래스고,
// 만약 소설의 출판사가 없을 때에는 NovelCreate를 통해 출판사를 db에 insert를 한다.
public class NovelRead extends NovelInfo {
    private DbOpenHelper dbOpenHelper;

    public NovelRead(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        String title = getTitle();
        int indexNun = 0;

        dbOpenHelper = getDbOpenHelper();


        Cursor isTitle = dbOpenHelper.selectSearch(title);
        isTitle.moveToFirst();
        Log.d("Tag", "title : " + isTitle.getCount());

        Cursor cursor = null;

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 혹시 소설이 없을 때
                if (isTitle.getCount() == 0) {
                    WebNovelManager webNovelManager = new WebNovelManager(dbOpenHelper);
                    webNovelManager.service(Name.SEARCH_CREATE, userInput);
                    Log.d("Tag", "isTitle.getCount == 0: NOVEL_CREATE 103");

                    //소설의 출판사가 없을 때, 출판사가 없다는 것은 top100이나 검색창에서 소설를 터치하지 않았다는 것
                } else if (isTitle.getString(10) == null) {
                    WebNovelManager webNovelManager = new WebNovelManager(dbOpenHelper);
                    webNovelManager.service(Name.NOVEL_CREATE, userInput);
                }

                // else if( 소설의 출판사가 있고, 이 소설을 크롤링한 지 하루가 지났다면 update하기

                Log.e("Tag", "title : " + isTitle.getString(10));
            }
        }).start();


        try {
            Thread.sleep(2000);

            cursor = dbOpenHelper.selectSearch(title);
            cursor.moveToFirst();

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
                Log.d("Tag", "totalEpisode : " + indexNun + " " + cursor.getString(8));
                Log.d("Tag", "commentTotalCount : " + indexNun + " " + cursor.getString(9));
                Log.d("Tag", "publisher : " + indexNun + " " + cursor.getString(10));

            } while (cursor.moveToNext());

            cursor.close();
            dbOpenHelper.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Name name() {
        return Name.NOVEL_READ;
    }
}
