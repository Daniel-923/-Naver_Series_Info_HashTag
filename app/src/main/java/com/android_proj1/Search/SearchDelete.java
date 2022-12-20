package com.android_proj1.Search;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public class SearchDelete extends SearchNovel {
    private DbOpenHelper dbOpenHelper;

    public SearchDelete(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);
    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.SEARCH_DELETE;
    }
}
