package com.android_proj1;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.jsoup.select.Elements;

public abstract class Crawler implements Runnable {
    protected String URL;
    private int current_category;
    private DbOpenHelper dbOpenHelper;
    protected int indexNum = 1;


    public Crawler(String url, DbOpenHelper dbOpenHelper) {
        this.URL = url;
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public void run() {
        setURL();
    }

    public int getCategory() {
        return current_category;
    }

    public void setCategory(int categoryCode) {
        current_category = categoryCode;
    }

    public abstract void setURL();


    public void crawl(Elements elements, Elements img_elements, Elements rank_elements) {

        // 제목, 링크(href)
        Elements h3_a = elements.select("a");
        //Log.d("Tag", "h3_a : " + div_comic_cont.select("a"));

        // 줄거리
        Elements p_dsc = elements.select("p.dsc");
        //Log.d("Tag", "p_dsc : " + div_comic_cont.select("p.dsc"));

        //점수, 작가, 총화 및 완결 여부
        Elements p_info = elements.select("p.info");
        
        // 점수
        Elements em_score = p_info.select("em.score_num");
        //Log.d("Tag", "em_score : " + div_comic_cont.select("em.score_num"));

        // 작가
        Elements span_author = p_info.select("span.author");
        //Log.d("Tag", "span_author : " + div_comic_cont.select("span.author"));


        // 총화 및 완결 여부
        //Elements span_ellipsis = elements.select("span.ellipsis");
        Elements span_ellipsis = p_info.select("span.ellipsis");


        //랭킹
        //SearchNovel에서 insert할 때 rank 필요하지 않음
        Elements span_rank = null;

        if (rank_elements != null) {
            span_rank = rank_elements.select("span.top_num");
        }

        ContentValues values_insert = new ContentValues();
        ContentValues values_update = new ContentValues();

        Log.d("Tag", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"); //로그캣 출력
        Log.d("Tag", "크롤링 시작"); //로그캣 출력


        String pretreatment_title = h3_a.text();
        //String [] splited = pretreatment_title.split("총");
        //String title = splited[0].replaceAll(" \\(", "");


        // 크롤링한 제목 뒤에 [이 있으면 문자열 제거
        String[] splited_monopoly = pretreatment_title.split("\\[");
        String title = splited_monopoly[0];


        // [독점] 제거한 제목에 [선공개]가 있으면 문자열 제거
        if (title.contains("[선공개]")) {
            String[] splite = title.split("\\[선공개]");
            title = splite[0];
            //title = splited_monopoly[0].replaceAll(" \\(", "");
            Log.d("Tag", "splited_title1" + title); //로그캣 출력
        }

        // [독점] 제거한 제목에 [총 ~~화/완결]이 있으면 문자열 제거
        if (title.contains("(총")) {
            String[] splite = title.split("\\(총");
            title = splite[0];
            //title = splited_monopoly[0].replaceAll(" \\(", "");
            Log.d("Tag", "splited_title2" + title); //로그캣 출력
        }


        String link = h3_a.attr("href");
        String summary = p_dsc.text();
        String score = em_score.text();
        String author = span_author.text();
        String img = img_elements.attr("src");
        Integer rank = null;

        if (rank_elements != null) {
            rank = Integer.valueOf(span_rank.text());
        }

        String category = null;
        String totalEpisode = null;

        Log.d("Tag", "span_ellipsis.size: " + span_ellipsis.text()); //로그캣 출력
        Log.d("Tag", "span_ellipsis.size: " + span_ellipsis.size()); //로그캣 출력


        // Top100, 검색 창에서 나오는 총 화를 구하기 위해, 만약 무료~라고 뜨면 그거까지 제외해서 크롤링
        if(span_ellipsis.size() == 2) {
            totalEpisode = span_ellipsis.get(span_ellipsis.size() - 2).text();
        }
        else if(span_ellipsis.size() == 1)
            totalEpisode = span_ellipsis.get(span_ellipsis.size() - 1).text();

        Log.d("Tag", "totalEpisode: " + totalEpisode); //로그캣 출력

        switch (getCategory()) {
            case 208:
                category = "현판";
                break;
            case 202:
                category = "판타지";
                break;
            case 207:
                category = "로판";
                break;
            case 201:
                category = "로멘스";
                break;
            case 206:
                category = "무협";
                break;
        }

        // rank랑 category는 img처럼 매개변수 받아서 ContentValues에 넣기
        // 넣기 전에 rank랑 category 값이 있는지 확인하고 없으면 SearchNovel에서 실행한 것이니 0으로 설정한 뒤 ContentValues에 넣기


        Log.d("Tag", "crawl_title : " + title); //로그캣 출력
/*        Log.d("Tag", "link : " + h3_a.attr("href")); //로그캣 출력
        Log.d("Tag", "summary : " + p_dsc.text()); //로그캣 출력
        Log.d("Tag", "score : " + em_score.text()); //로그캣 출력
        Log.d("Tag", "author : " + span_author.text()); //로그캣 출력
        Log.d("Tag", "img : " + img_elements.attr("src")); //로그캣 출력
        Log.d("Tag", "rank : " + span_rank.text()); //로그캣 출력
        Log.d("Tag", "category : " + category); //로그캣 출력*/

        //Log.d("Tag", "totalEpisode : " + totalEpisode);

        values_insert.put(TableInfo.COLUMN_TITLE, title);
        values_insert.put(TableInfo.COLUMN_LINK, link);
        values_insert.put(TableInfo.COLUMN_SUMMARY, summary);
        values_insert.put(TableInfo.COLUMN_SCORE, score);
        values_insert.put(TableInfo.COLUMN_AUTHOR, author);
        values_insert.put(TableInfo.COLUMN_IMG, img);
        values_insert.put(TableInfo.COLUMN_RANK, rank);
        values_insert.put(TableInfo.COLUMN_CATEGORY, category);
        values_insert.put(TableInfo.COLUMN_TOTALEPISODE, totalEpisode);


        //크롤링한 소설의 제목(title)이 DB에 있는 확인
        try {
            Cursor cursor = dbOpenHelper.selectTitle(title);

            //처음 init할 때 DB에 저장된 값이 없는 상태에서 title를 읽을 경우 cursor는 0으로 return. 따라서 0이면 insert
            if (cursor.getCount() == 0) {
                dbOpenHelper.insertTop100Search(values_insert);
            }


            if (cursor != null) {

                while (cursor.moveToNext()) {

                    String titleDB = cursor.getString(0);
                    Log.d("Tag", "titleDB : " + titleDB); //로그캣 출력
                    Log.d("Tag", "title : " + title); //로그캣 출력

                    if (titleDB.equals(title)) {
                        Log.d("Tag", "titleDB.equals yes : " + indexNum + " " + titleDB.equals(title)); //로그캣 출력

                        if (rank_elements != null) {
                            values_update.put(TableInfo.COLUMN_RANK, rank);
                        }
                        values_update.put(TableInfo.COLUMN_CATEGORY, category);
                        dbOpenHelper.updateTop100(values_update, title);

                    } else {
                        Log.d("Tag", "titleDB.equals : no " + indexNum); //로그캣 출력
                        dbOpenHelper.insertTop100Search(values_insert);
                    }
                    indexNum++;
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


