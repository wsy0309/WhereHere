package example.wherehere;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by user on 2017-12-04.
 */

@SuppressWarnings("serial")
/*
   private static final long serialVersionUID = 4220461820168818967L; ë“±ìœ¼ë¡œ
   UIdë¥¼ generateí•´ì„œ ì‚¬ìš©í•´ì•¼ë˜ë‚˜ studioì—ëŠ” ê¸°ë³¸ ì œê³µë˜ëŠ” generateê°€ ì—†ê¸°ë•Œë¬¸ì—
   ìœ„ì˜ annotaionì„ ì¶”ê°€í•˜ë©´ë©ë‹ˆë‹¤.
 */
public class MyItem implements Serializable {

    // 아이템 타입을 구분하기 위한 type 변수.
    private int type ;
    //listview_tab item
    private  String tabStation;
    //listview_recom item
    private Drawable recomImg;
    private String recomText;
    private String recomTime;
    //listview_dialog item
    private Drawable startImg;
    private String startText;
    //listview_detail1 item
    private String StrtEnd;
    private String StrtEndStation;
    private String StrtEndTime;
    private String StrtEndCost;
    //listview_detail2 item
    private Drawable typeImg;
    private String typeNum;
    private String subStart;
    private String subEnd;
    private String subTime;
    private String subDist;



    /***********************
     *      getter
     ***********************/
    public int getType() {
        return type;
    }

    public String getTabStation() {return tabStation;}

    public Drawable getRecomImg() {
        return recomImg;
    }
    public String getRecomText() {
        return recomText;
    }
    public String getRecomTime() {
        return recomTime;
    }

    public Drawable getStartImg() {
        return startImg;
    }
    public String getStartText() {
        return startText;
    }

    public String getStrtEnd() {
        return StrtEnd;
    }
    public String getStrtEndStation() {
        return StrtEndStation;
    }
    public String getStrtEndTime() {
        return StrtEndTime;
    }
    public String getStrtEndCost() {
        return StrtEndCost;
    }

    public Drawable getTypeImg() {
        return typeImg;
    }
    public String getSubDist() {
        return subDist;
    }
    public String getSubStart() {
        return subStart;
    }
    public String getSubEnd() {
        return subEnd;
    }
    public String getSubTime() {
        return subTime;
    }
    public String getTypeNum() {
        return typeNum;
    }

    /***********************
     *      setter
     ***********************/
    public void setType(int type) {
        this.type = type;
    }

    public void setTabStation(String tabStation) {
        this.tabStation = tabStation;
    }

    public void setRecomImg(Drawable stationImg) {
        this.recomImg = stationImg;
    }
    public void setRecomText(String stationText) {
        this.recomText = stationText;
    }
    public void setRecomTime(String recomTime) {
        this.recomTime = recomTime;
    }

    public void setStartImg(Drawable startImg) {
        this.startImg = startImg;
    }
    public void setStartText(String startText) {
        this.startText = startText;
    }

    public void setStrtEnd(String strtEnd) {
        StrtEnd = strtEnd;
    }
    public void setStrtEndCost(String strtEndCost) {
        StrtEndCost = strtEndCost;
    }
    public void setStrtEndStation(String strtEndStation) {
        StrtEndStation = strtEndStation;
    }
    public void setStrtEndTime(String strtEndTime) {
        StrtEndTime = strtEndTime;
    }

    public void setSubDist(String subDist) {
        this.subDist = subDist;
    }
    public void setSubStart(String subStart) {
        this.subStart = subStart;
    }
    public void setSubEnd(String subEnd) {
        this.subEnd = subEnd;
    }
    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }
    public void setTypeImg(Drawable typeImg) {
        this.typeImg = typeImg;
    }
    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }

}
