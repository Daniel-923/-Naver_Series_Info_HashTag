package com.android_proj1.Search;

import com.android_proj1.DbOpenHelper;
import com.android_proj1.UserInput;

public class SearchUpdate extends SearchNovel {
    private DbOpenHelper dbOpenHelper;

    public SearchUpdate(DbOpenHelper dbOpenHelper) {
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

        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Name name() {
        return Name.SEARCH_UPDATE;
    }

    public void run() {
        SearchThread searchThread = new SearchThread(getSearch_URL(), dbOpenHelper);
        Thread crawlSearch = new Thread(searchThread);
        crawlSearch.start();
    }
}
