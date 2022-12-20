package com.android_proj1.NovelInfo;


import android.database.Cursor;
import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public abstract class NovelInfo implements WebNovelService {


    private String NovelInfo_URL = "https://series.naver.com";
    private DbOpenHelper dbOpenHelper;
    private UserInput userInput;
    private String title;
    private String link;

    public NovelInfo(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public void service(UserInput userInput) {
        this.title = (String) userInput.getMap().get("title");
        this.userInput = userInput;
    }

    public DbOpenHelper getDbOpenHelper() {
        return this.dbOpenHelper;
    }

    public String getTitle() {
        return this.title;
    }


    public void setURL(String title) {


        Cursor cursor = dbOpenHelper.selectSearch(title);

        cursor.moveToFirst();

        int indexNun = 0;

        indexNun++;
        Log.d("Tag", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"); //로그캣 출력
        Log.d("Tag", "title : " + indexNun + " " + cursor.getString(0));
        Log.d("Tag", "link : " + indexNun + " " + cursor.getString(1));

        this.link = cursor.getString(1);

        NovelInfo_URL += link;

    }

    public String getURL() {

        return this.NovelInfo_URL;
    }


}





/*

## Novel

사용자가 소설을 터치(클릭)했을 때 실행
터치함으로써 터치한 소설의 정보와 댓글 반응, 베스트 댓글 등을 볼 수 있음


DB함수
- 소설정보 테이블
1. updateInfo(commentTotalCount, publisher, category)
2. insertInfo(title, commentTotalCount, publisher, category)
3. readInfo()

- 댓글 테이블
//업데이트 한 뒤 하나의 소설의 댓글로만 이루어진 csv파일 생성하기 -> 모델에 입력하기
1. insertComment(comment, title, id)
2. readComment()

- 댓글 반응 테이블
//모델의 출력값을 통해 업데이트
1. updateCommentReact(title, isCommentReact, emotion...)
2. insertCommentReact(title, isCommentReact, emotion...)
3. readCommentReact()



// 여기 클래스의 크롤링 함수는 Top100과 SearchNovel과 다르게 따로 만들어야 함. 즉 독립적


//NovelInfo 클래스 내에서의 실행문
먼저 commentReact 테이블의 댓글 반응 여부를 찾아냄

//댓글 반응여부가 없는 것은 이 소설의 정보가 없다는 것이다
if(댓글 반응 여부가 false이면) {
  해당 소설 정보를 크롤링하는 함수 실행. //아래에 크롤링 설명 참고
  리턴 받은 insert용 ContentValues를 통해 InsertInfo()로 DB에 저장하기
  InsertInfo(commentTotalCount, publisher, category)

  해당 소설 댓글을 크롤링하는 함수 실행.  //아래에 크롤링 설명 참고
  리턴 받은 댓글용 ContentValues를 통해 InsertInfo()로 댓글 테이블에 저장. csv파일 생성
  insertComment(comment, title, id)

  insertComment을 통해 얻은 댓글로 만들어진 csv파일을 모델에 넣어 출력으로 받은 감정을 댓글 감정 테이블에 저장한다.
  insertCommentReact(title, isCommentReact, emotion...)
}

else if(댓글 반응 여부가 true이면서 갱신시간이 24시간 지났으면) {
  크롤링 함수를 실행. 리턴 받은 3개 중 update용과 댓글용 ContentValues를 통해 updateInfo(), insertComment() 함수를 실행한다.
  insertComment를 실행하면 csv파일을 생성한다.
  그다음 updateCommentReact() 실행(모델에 csv파일 넣고 출력값 받고)
}

//댓글 반응 여부 true && 갱신시간 안지남
else {
  DB 읽어서 정보, 댓글, 댓글 반응 보여주기
  readInfo()
  readComment()
  readCommentReact

}





NovelInfo, 댓글, 댓글 반응을 크롤링하는 함수를 실행. 그리고 contentValues들을 리턴
여러개 리턴하는 것은 http://daplus.net/java-java-%EB%A9%94%EC%86%8C%EB%93%9C%EC%97%90%EC%84%9C-2-%EA%B0%9C%EC%9D%98-%EA%B0%92%EC%9D%84-%EB%B0%98%ED%99%98%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95%EC%9D%80-%EB%AC%B4%EC%97%87%EC%9E%85%EB%8B%88/


crawlNovelInfo.start()
crawlNovelComment.start()
crawlNovelCommentReact.start()


//소설 정보 크롤링 함수 내에서의 조건문
contentClaues를 3개 생성. 하나는 insert용, 다른 하나는 update용, 마지막 하나는 댓글용
만약 ContentValues가 생성되어있으면 생성하지말고 3개 다 초기화

if(크롤링한 소설 제목이 DB에 있는지)
  DB에 있으면 update용 ContentValues에 저장

//댓글과 완전히 같은지 판단 ex) "이 소설 사이다다"라는 댓글이 DB에 있는데, 크롤링한 댓글이 "이 소설 사이다다네요"라면 insert를 해야함.
else if(크롤링한 소설 제목의 댓글이 DB에 있는지)
  DB에 있으면 continue

else
  NovelInfo에서 크롤링한 소설 제목이 DB에 없는 소설 제목이면 insert용 ContentValues에 저장
  그리고 크롤링한 소설 제목의 댓글을 insertComment에 넣을 수 있게 댓글용 ContentValues에 저장











*/
