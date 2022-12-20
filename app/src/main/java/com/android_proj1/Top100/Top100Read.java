package com.android_proj1.Top100;

import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;

public class Top100Read extends Top100Novel {
    private DbOpenHelper dbOpenHelper;

    public Top100Read(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        int indexNun = 0;

        // userInput 풀기
        // String, Object -> Category, Object
        // Object란 사용자가 클릭한 장르
        String category = (String) userInput.getMap().get("category");


        dbOpenHelper = getDbOpenHelper();
        Cursor cursor = dbOpenHelper.selectTop100(category);
        cursor.moveToFirst();

        if (cursor != null) {

            do {
                // 현판 -> 판타지 -> 로판 -> 로멘스 -> 무협
                // 장르 별로 1~100등 순으로 열의 데이터를 가져온다.
                indexNun++;
                Log.d("Tag", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"); //로그캣 출력
                Log.d("Tag", "title : " + indexNun + " " + cursor.getString(0));
                Log.d("Tag", "link : " + indexNun + " " + cursor.getString(1));
                Log.d("Tag", "summary : " + indexNun + " " + cursor.getString(2));
                Log.d("Tag", "score : " + indexNun + " " + cursor.getString(3));
                Log.d("Tag", "author : " + indexNun + " " + cursor.getString(4));
                Log.d("Tag", "img : " + indexNun + " " + cursor.getString(5));
                Log.d("Tag", "rank : " + indexNun + " " + cursor.getString(6) + "위");
                Log.d("Tag", "category : " + indexNun + " " + cursor.getString(7));
            }
            while (cursor.moveToNext());


        }

        cursor.close();
        dbOpenHelper.close();

    }

    @Override
    public Name name() {
        return Name.TOP100_READ;
    }
}
