package com.android_proj1.Top100;

import android.util.Log;

import com.android_proj1.Crawler;
import com.android_proj1.DbOpenHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class Top100Thread extends Crawler {

    public Top100Thread(String URL, DbOpenHelper dbOpenHelper) {
        super(URL, dbOpenHelper);
    }


    @Override
    public void setURL() {
        Document doc;
        try {

            //현판, 판타지, 로판, 로멘스, 무협 순
            int categoryArr[] = {208, 202, 207, 201, 206};
            int pageArr[] = {1, 2, 3, 4, 5};

            for (int categoryCode : categoryArr) {

                String category_URL = String.format(URL + "%d" + "&page=", categoryCode);
                Log.d("Tag", "category_URL : " + String.format(URL + "%d" + "&page=", categoryCode));

                setCategory(categoryCode);
                Log.d("Tag", "categoryCode : " + categoryCode);

                for (int page : pageArr) {
                    String Top100_URL = String.format(category_URL + "%d", page);
                    Log.d("Tag", "URL : " + String.format(category_URL + "%d", page));

                    doc = Jsoup.connect(Top100_URL).get();

                    //Top100 페이지 당 존재하는 소설 개수를 가져옴
                    Elements novel_Elements = doc.select("ul.comic_top_lst > li");
                    int listSize = novel_Elements.size();
                    Log.d("Tag", "Top100_pageNum size : " + novel_Elements.size());

                    for(Element elements1 : novel_Elements) {

                        //이미지 외 들어있는 박스
                        Elements elements = elements1.select("div.comic_cont");
                        //Log.d("Tag", "div_comic_cont : " + contents.select("div.comic_cont"));

                        //이미지
                        Elements img_elements = elements1.select("img");
                        //Log.d("Tag", "a_img : " + doc.select("img"));

                        //랭킹
                        Elements rank_elements = elements1.select("div.top_numb");

                        //공통 함수 실행
                        crawl(elements, img_elements, rank_elements);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}