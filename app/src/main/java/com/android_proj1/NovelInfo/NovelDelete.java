package com.android_proj1.NovelInfo;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public class NovelDelete extends NovelInfo {
    public NovelDelete(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);
    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.NOVEL_DELETE;
    }
}
