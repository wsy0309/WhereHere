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

    // ì•„ì´í…œ íƒ€ìž…ì„ êµ¬ë¶„í•˜ê¸° ìœ„í•œ type ë³€ìˆ˜.
    private int type ;
    //listview_tab item
    private  String tabStation;
    //listview_recom item
    private Drawable stationImg;
    private String stationText;
    private String time;
    //listview_dialog item
    private Drawable startImg;
    private String startText;

    /***********************
     *      getter
     ***********************/
    public int getType() {
        return type;
    }

    public String getTabStation() {return tabStation;}

    public Drawable getStationImg() {
        return stationImg;
    }
    public String getStationText() {
        return stationText;
    }
    public String getTime() {
        return time;
    }

    public Drawable getStartImg() {
        return startImg;
    }
    public String getStartText() {
        return startText;
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

    public void setStationImg(Drawable stationImg) {
        this.stationImg = stationImg;
    }
    public void setStationText(String stationText) {
        this.stationText = stationText;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public void setStartImg(Drawable startImg) {
        this.startImg = startImg;
    }
    public void setStartText(String startText) {
        this.startText = startText;
    }

}
