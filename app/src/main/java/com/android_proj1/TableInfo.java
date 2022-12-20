package com.android_proj1;

public class TableInfo {

    public static final String TABLE_NAME = "novelInfo";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TOTALEPISODE = "totalEpisode";
    public static final String COLUMN_COMMENTTOTALCOUNT = "commentTotalCount";
    public static final String COLUMN_PUBLISHER = "publisher";


}

/*

# DB Table

외래키 관계는 SUPPLIERS or RESTRICT

## 소설 정보 테이블

title(P), link, summary, score, author, img, rank, category, publisher, totalEpisode, commentTotalCount


## 댓글 테이블

comment_id(P), comment, title(F)


## 댓글 반응 테이블

title(F, P), isCommentReact, 지루함, 감동적, 슬픔, 재미, ...




ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


# Class

## Top100

초기화를 통해 DB에 초기값을 insert한다.
그럼으로써 다음부터는 update를 통해 DB 값을 수정할 수 있다.

초기화할 때 값이 없는 것은 null로 저장해야 함. 그래야 update 할 수 있음



### 소설정보 테이블

- 초기화

처음 초기화할 때 Top100 소설을 크롤링하는 함수를 실행.
insertTop100(title, link, summary, score, author, img, rank, category)


- 초기화 이후

특정 시간 or 함수 실행한 시간을 구해서 일정시간 넘어가면 크롤링 실행

크롤링하기 전 rank는 null or 0으로 초기화

//크롤링 했을 때
if(크롤링한 소설 제목이 DB에 있는지)
  DB에 있으면 순위만 업데이트, 결과적으로 다 업데이트되긴 하지만 변하는 것은 rank만 변함. 다른 값은 불변
  replaceTop100(title, link, summary, score, author, img, rank, category)

//Top100에서 크롤링한 소설 제목이 DB에 없는 소설 제목이면 insert
else
  insertTop100(title, link, summary, score, author, img, rank, category)






## Search

검색 버튼 이벤트에 의해서 실행
사용자가 입력한 제목을 받음

### 소설정보 테이블

if(사용자가 입력한 제목의 소설이 DB에 없으면) {
    해당하는 제목을 크롤링

    // 예외 처리
    // 예를 들어 사용자가 제목을 짧게 입력했을 때는 DB에서 찾지 못했는데 크롤링 했을 때는 DB에서 찾을 수 있으니 예외처리 넣었다.
    if(크롤링한 제목이 DB에 있으면)
      continue;

    insertSearch(title, link, summary, score, author, img)
    }
else
    db에서 read -> 사용자에게 보여줌






## Novel

사용자가 소설을 터치(클릭)했을 때 실행
터치함으로써 터치한 소설의 정보와 댓글 반응, 베스트 댓글 등을 볼 수 있음

if(댓글 반응 여부가 false이면) {
  해당 소설 정보를 크롤링하여 DB에 저장하기
  InsertInfo()

  크롤링 함수를 통해 댓글을 얻어 댓글 테이블에 저장. csv파일 생성
  insertComment(comment, title, id)

  insertComment을 통해 얻은 댓글로 만들어진 csv파일을 모델에 넣어 출력으로 받은 감정을 댓글 감정 테이블에 저장한다.
  insertCommentReact(title, isCommentReact, emotion...)
}

else if(댓글 반응 여부가 true이면서 갱신시간이 24시간 지났으면) {
  updateInfo(), updateComment(), updateCommentReact() 실행(모델에 csv파일 넣고 출력값 받고)
}

//댓글 반응 여부 true && 갱신시간 안지남
else {
  DB에서 꺼내서 보여주기
  readInfo()
}



### 소설정보 테이블
updateInfo(totalEpisode, commentTotalCount, publisher, category)

insertInfo(totalEpisode, commentTotalCount, publisher, category)



### 댓글 테이블

//업데이트 한 뒤 하나의 소설의 댓글로만 이루어진 csv파일 생성하기 -> 모델에 입력하기
updateComment(comment, title, id)

insertCommentReact(title, isCommentReact, emotion...)


### 댓글 반응 테이블
//모델의 출력값을 통해 업데이트
updateCommentReact(title, isCommentReact, emotion...)


insertCommentReact(title, isCommentReact, emotion...)

*/

