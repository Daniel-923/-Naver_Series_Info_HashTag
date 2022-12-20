package com.android_proj1;

import com.android_proj1.NovelInfo.NovelCreate;
import com.android_proj1.NovelInfo.NovelDelete;
import com.android_proj1.NovelInfo.NovelRead;
import com.android_proj1.NovelInfo.NovelUpdate;
import com.android_proj1.Search.SearchCreate;
import com.android_proj1.Search.SearchDelete;
import com.android_proj1.Search.SearchRead;
import com.android_proj1.Search.SearchUpdate;
import com.android_proj1.Top100.Top100Create;
import com.android_proj1.Top100.Top100Delete;
import com.android_proj1.Top100.Top100Read;
import com.android_proj1.Top100.Top100Update;

import java.util.ArrayList;
import java.util.List;

public class WebNovelManager {
    private DbOpenHelper dbOpenHelper;
    private List<WebNovelService> list;

    public WebNovelManager(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
        this.list = new ArrayList<>();

        this.list.add(new Top100Create(dbOpenHelper));
        this.list.add(new Top100Read(dbOpenHelper));
        this.list.add(new Top100Update(dbOpenHelper));
        this.list.add(new Top100Delete(dbOpenHelper));

        this.list.add(new SearchCreate(dbOpenHelper));
        this.list.add(new SearchRead(dbOpenHelper));
        this.list.add(new SearchUpdate(dbOpenHelper));
        this.list.add(new SearchDelete(dbOpenHelper));

        this.list.add(new NovelCreate(dbOpenHelper));
        this.list.add(new NovelRead(dbOpenHelper));
        this.list.add(new NovelUpdate(dbOpenHelper));
        this.list.add(new NovelDelete(dbOpenHelper));
    }

    public List<WebNovelService> getList() {
        return this.list;
    }


    public void service(WebNovelService.Name name, UserInput userInput) {
        for (WebNovelService s : list) {
            if(s.name() == name) {
                s.service(userInput);
            }
        }
    }






}

