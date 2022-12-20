package com.android_proj1.Top100;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;

public class Top100Update extends Top100Novel {
    private DbOpenHelper dbOpenHelper;

    public Top100Update(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        dbOpenHelper = getDbOpenHelper();

        // 호출하는 곳에서 반복문을 통해 해당 시간이 지나갔을 때 Top100Update.service() 실행

        // 크롤링 함수 시작하기 전 DB의 rank열 값들을 초기화하기
        dbOpenHelper.updateRankColum();

        // Top100 크롤링 시작 함수
        run();
    }

    @Override
    public Name name() {
        return Name.TOP100_UPDATE;
    }

    public void run() {
        // Top100 크롤링 시작 함수
        Top100Thread top100Thread = new Top100Thread(getTop100_URL(), dbOpenHelper);
        Thread crawlTop100 = new Thread(top100Thread);
        crawlTop100.start();
    }
}
