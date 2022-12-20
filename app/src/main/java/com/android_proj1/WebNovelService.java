package com.android_proj1;

public interface WebNovelService {


    public enum Name {
        TOP100_CREATE, TOP100_READ, TOP100_UPDATE, TOP100_DELETE,
        SEARCH_CREATE, SEARCH_READ, SEARCH_UPDATE, SEARCH_DELETE,
        NOVEL_CREATE, NOVEL_READ, NOVEL_UPDATE, NOVEL_DELETE;
    }

    public void service(UserInput userInput);
    public Name name();
}
