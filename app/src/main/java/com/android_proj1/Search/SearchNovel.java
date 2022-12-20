package com.android_proj1.Search;


//alt + insert

import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public abstract class SearchNovel implements WebNovelService {
    private String Search_URL = "https://series.naver.com/search/search.series?t=novel&q=";
    private DbOpenHelper dbOpenHelper;
    private String title;


    public SearchNovel(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }


    public DbOpenHelper getDbOpenHelper() {
        return this.dbOpenHelper;
    }

    public void setSearch_URL(String title) {
        Search_URL += title + "&so=rel.dsc&page=";

    }

    public String getSearch_URL() {
        return Search_URL;
    }


    @Override
    public void service(UserInput userInput) {
        this.title = (String) userInput.getMap().get("title");
    }


    public String getTitle() {
        return this.title;
    }
}






/*
## Search

검색 버튼 이벤트에 의해서 실행
사용자가 입력한 제목을 받음

DB함수
//Search에서는 rank와 category를 크롤링할 수 없으니 0으로 값을 설정해서 insert한다
1. insertTop100Search(title, link, summary, score, author, img, rank, category) //Top100과 Search에서 사용
2. readSearch()


//SerchNovel 클래스 내에서의 실행문
if(사용자가 입력한 제목의 소설이 DB에 없으면) {
    크롤링 시작
    crawlSearch.start()  //아래에 크롤링 설명 참고


    이후 받은 insert용 ContentValues로 insertSearch()을 실행
    insertTop100Search(title, link, summary, score, author, img, rank, category)

    }
else
    db에서 read -> 사용자에게 보여줌
    readSearch()


//크롤링 함수 내에서의 조건문
만약 ContentValues가 생성되어있으면 생성하지말고 2개 다 초기화

if(크롤링한 소설 제목이 DB에 있는지)
  continue

else
  크롤링한 소설 제목이 DB에 없는 소설 제목이면 rank, category값은 0으로 설정해놓고 insert용 ContentValues에 저장
  이후 ContentValues 리턴



// read()
보여줄 때는 DB에서 긁어와서 사용자가 입력한 제목이나 단어에 해당하는 소설들을 보여줌




*/










