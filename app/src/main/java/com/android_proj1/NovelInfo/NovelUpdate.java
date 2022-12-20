package com.android_proj1.NovelInfo;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;

public class NovelUpdate extends NovelInfo {
    private DbOpenHelper dbOpenHelper;

    public NovelUpdate(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        String title = getTitle();
        setURL(title);

        dbOpenHelper = getDbOpenHelper();
        run();
    }

    @Override
    public Name name() {
        return Name.NOVEL_UPDATE;
    }

    public void run() {
        NovelInfoThread novelInfoThread = new NovelInfoThread(getURL(), dbOpenHelper, getTitle());
        Thread novelInfo = new Thread(novelInfoThread);
        novelInfo.start();
    }
}
