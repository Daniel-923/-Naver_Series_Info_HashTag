package com.android_proj1.Top100;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public class Top100Delete extends Top100Novel {
    public Top100Delete(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);
    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.TOP100_DELETE;
    }
}
