package com.android_proj1.Top100;


import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public abstract class Top100Novel implements WebNovelService {


    private String Top100_URL = "https://series.naver.com/novel/top100List.series?rankingTypeCode=WEEKLY&categoryCode=";
    private DbOpenHelper dbOpenHelper;


    public String getTop100_URL() {
        return Top100_URL;
    }

    public Top100Novel(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public DbOpenHelper getDbOpenHelper() {
        return this.dbOpenHelper;
    }

    public void a() {
        // 공통 선택 작업
    }




    @Override
    public void service(UserInput userInput) {}
}


/*    public void initTop100() {

        // Top100 크롤링 시작 함수
        Thread crawlTop100 = new Thread(new Top100Thread(getTop100_URL(), this));
        crawlTop100.start();




        *//*
        - Top100에서의 insert와 update, 그리고 SearchNovel에서의 insert는 CrawlData에서 리턴 주는 ContentValues을 그대로 인수로 넣어서 실행해야 될 것 같음.

        - 그럼 DbOpenHelper에서 insertTop100Search, updateTop100Search는 매개변수로 ContentValues를 받을 거임.
        *//*

    }


    public void renewTop100() {

        // 호출하는 곳에서 반복문을 통해 해당 시간이 지나갔을 때 renewTop100() 실행


        // 크롤링 함수 시작하기 전 DB의 rank열 값들을 초기화하기

        dbOpenHelper.updateRankColum();

        // 먼저 Top100 크롤링 시작 함수
        Thread crawlTop100 = new Thread(new Top100Thread(getTop100_URL(), this));
        crawlTop100.start();


    }*/


/*
## Top100

초기화를 통해 DB에 초기값을 insert한다. 장르별 Top100을 insert

DB함수
1. insertTop100Search(title, link, summary, score, author, img, rank, category) //Top100과 Search에서 사용
2. updateTop100(title, link, summary, score, author, img, rank, category)
3. readTop100()


//초기화
- initTop100()

처음 초기화할 때 Top100 소설을 크롤링하는 함수를 실행. 그리고 contentValues를 리턴
crawlTop100.start()

이후 받은 ContentValues 중 insert용 ContentValues으로 insertTop100()을 실행...
insertTop100Search(title, link, summary, score, author, img, rank, category)


- renewTop100()

특정 시간 or 함수 실행한 시간을 구해서 일정시간 넘어가면 크롤링 실행

크롤링하기 전 rank는 null or 0으로 초기화 // DB를 전체 다 돌지 고민 중...

Top100 소설을 크롤링하는 함수를 실행. 그리고 크롤링 끝나면 contentValues를 리턴받아 insert/update 함수 실행
crawlTop100.start()


//크롤링 함수 내에서의 조건문
contentClaues를 2개 생성. 하나는 insert용, 다른 하나는 update용
만약 ContentValues가 생성되어있으면 생성하지말고 2개 다 초기화

if(크롤링한 소설 제목이 DB에 있는지)
  DB에 있으면 순위만 업데이트할 수 있게 update용 ContentValues에 저장

else
  Top100에서 크롤링한 소설 제목이 DB에 없는 소설 제목이면 insert용 ContentValues에 저장


//updateTop100 클래스 내에서의 실행문
  이후 리턴 받은 ContentValues로 insertTop100() 실행
  updateTop100(title, link, summary, score, author, img, rank, category)

  이후 리턴 받은 ContentValues로 insertTop100() 실행
  insertTop100Search(title, link, summary, score, author, img, rank, category)






ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//크롤링한 소설 제목이 DB에 있으면 update를, 제목이 DB에 없으면 insert
//하지만 update를 할 때는 기존 레코드를 다 지우고, 새로 레코드를 생성한다.

replaceTop100(title, link, summary, score, author, img, rank, category)
ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


readTop100()


*/



