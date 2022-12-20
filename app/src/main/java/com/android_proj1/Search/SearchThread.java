package com.android_proj1.Search;

import android.util.Log;

import com.android_proj1.Crawler;
import com.android_proj1.DbOpenHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class SearchThread extends Crawler {

    public SearchThread(String URL, DbOpenHelper dbOpenHelper) {
        super(URL, dbOpenHelper);
    }

    @Override
    public void setURL() {
        // 공통함수
        {
            Document doc;
            int pass = 0;
            try {
                for (int i = 1; i < 5; i++) {
                    //URL.replace("pageNum", "i");

                    if (pass == 1) {
                        break;
                    }

                    String Search_URL = String.format(URL + "%d", i);
                    Log.d("Tag", "URL : " + String.format(URL + "%d", i));

                    // 스레드 밖에서 사용자가 입력한 제목 이름을 url에 붙이기   현재는 재벌
                    doc = Jsoup.connect(Search_URL).get();

                    Elements pageNum = doc.select("p.pagenate > a");
                    int pageSize = pageNum.size();
                    Log.d("Tag", "pageSize : " + pageSize);
                    
                    // 검색 창의 페이지가 1일 경우
                    if (pageSize == 0) {
                        pass = 1;
                    }

                    //Search 페이지 당 존재하는 소설 개수를 가져옴
                    Elements novel_Elements = doc.select("ul.lst_list > li");
                    //int listSize = novel_Elements.size();
                    Log.d("Tag", "Search_pageNum size : " + novel_Elements.size());
                    //Log.d("Tag", "novel_Elements : " + novel_Elements);


                    for(Element elements1 : novel_Elements) {

                        //Log.d("Tag", "novel_Elements : " + elements1.select("div.cont"));

                        Elements elements = elements1.select("div.cont");
                        //Log.d("Tag", "div_cont : " + ul_lst_list.select("div.cont"));

                        //이미지
                        Elements img_elements = elements1.select("img");
                        //Log.d("Tag", "a_img : " + doc.select("img"));


                        //이 클래스에서는 사용하지 않음. 공통 함수 crawl에서 null인 경우 처리함.
                        Elements rank_elements = null;


                        //공통 함수 실행
                        crawl(elements, img_elements, rank_elements);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}