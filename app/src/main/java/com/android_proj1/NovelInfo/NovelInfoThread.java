package com.android_proj1.NovelInfo;

import android.content.ContentValues;
import android.provider.Settings;
import android.util.Log;

import com.android_proj1.Crawler;
import com.android_proj1.DbOpenHelper;
import com.android_proj1.TableInfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NovelInfoThread extends Crawler {

    private DbOpenHelper dbOpenHelper;
    private String title;


    public NovelInfoThread(String URL, DbOpenHelper dbOpenHelper, String title) {
        super(URL, dbOpenHelper);
        this.dbOpenHelper = dbOpenHelper;
        this.title = title;
    }

    @Override
    public void setURL() {

        crawlInfo();
    }


    public void crawlInfo() {


        Document doc;

        try {
            // 스레드 밖에서 사용자가 입력한 제목 이름을 url에 붙이기   현재는 재벌
            doc = Jsoup.connect(URL).get();

            Elements elements = doc.select("li.info_lst");
            Elements elements1 = elements.select("a");
            String publisher = elements1.get(1).text();

            Log.d("Tag", "출판사: " + elements1.get(1).text());


            ContentValues values_update = new ContentValues();
            values_update.put(TableInfo.COLUMN_PUBLISHER, publisher);


            dbOpenHelper.updateTop100(values_update, title);

            Log.d("Tag", "출판사 삽입 완료");

            //댓글을 여기서 크롤링하지 못하기에 여기서 db에 접근하여 출판사를 insert한다.
            //댓글이 크롤링 되었다면 Crawler 클래스에 다른 함수를 만들어 했을 듯...



            //https://stackoverflow.com/questions/36111805/how-to-make-jsoup-wait-for-the-complete-pageskip-a-progress-page-to-load
            //안드로이드에 maven추가해서 selenium 의존성 추가하고 웹드라이버 클래스로 읽어온 다음 jsoup으로 긁어오기 
            // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java/4.7.2








        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
