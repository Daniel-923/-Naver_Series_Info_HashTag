package com.android_proj1.Search;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;
import com.android_proj1.WebNovelService;

public class SearchCreate extends SearchNovel {
    private DbOpenHelper dbOpenHelper;

    public SearchCreate(DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper);
    }

    @Override
    public void service(UserInput userInput) {
        super.service(userInput);

        String title = getTitle();

        //Search
        setSearch_URL(title);

        dbOpenHelper = getDbOpenHelper();
        run();

    }

    @Override
    public WebNovelService.Name name() {
        return WebNovelService.Name.SEARCH_CREATE;
    }

    public void run() {
        SearchThread searchThread = new SearchThread(getSearch_URL(), dbOpenHelper);
        Thread crawlSearch = new Thread(searchThread);
        crawlSearch.start();
    }

}
