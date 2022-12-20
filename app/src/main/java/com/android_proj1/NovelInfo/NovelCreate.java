package com.android_proj1.NovelInfo;

import android.util.Log;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

// 이미 있는 소설의 출판사는 크롤링할 필요가 없음
// 따라서 NovelCreate는 NovelRead에 의해서만 호출됨. 소설의 출판사가 없을 때만 호출됨.
// 현재는 업데이트와 기능이 비슷하지만, create는 처음 아무것도 없을 때 한 번 호출되는 것이고,
// update는 생성 이후 update만으로 바뀐 내용들을 수정한다.
public class NovelCreate extends NovelInfo {
    private DbOpenHelper dbOpenHelper;

    public NovelCreate(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        String title = getTitle();
        setURL(title);
        Log.d("tag", "getURL: " + getURL());

        dbOpenHelper = getDbOpenHelper();
        run();

    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.NOVEL_CREATE;
    }

    public void run() {
        NovelInfoThread novelInfoThread = new NovelInfoThread(getURL(), dbOpenHelper, getTitle());
        Thread novelInfo = new Thread(novelInfoThread);
        novelInfo.start();
    }


}
