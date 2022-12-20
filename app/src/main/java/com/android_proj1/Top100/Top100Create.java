package com.android_proj1.Top100;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public class Top100Create extends Top100Novel {
    private DbOpenHelper dbOpenHelper;

    public Top100Create(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        dbOpenHelper = getDbOpenHelper();

        // Top100 크롤링 시작 함수
        run();

    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.TOP100_CREATE;
    }

    public void run() {
        // Top100 크롤링 시작 함수
        Top100Thread top100Thread = new Top100Thread(getTop100_URL(), dbOpenHelper);
        Thread crawlTop100 = new Thread(top100Thread);
        crawlTop100.start();
    }

}
