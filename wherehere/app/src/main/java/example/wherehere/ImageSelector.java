package example.wherehere;

/**
 * Created by user on 2017-12-06.
 */

public class ImageSelector {


    public int selectImage(StationPoint stationPoint) {

        int id = 0;

        if (stationPoint.getSubwayNum() == 1) {
            id = R.drawable.linenum1;
        } else if (stationPoint.getSubwayNum() == 2) {
            id = R.drawable.linenum2;
        } else if (stationPoint.getSubwayNum() == 3) {
            id = R.drawable.linenum3;
        } else if (stationPoint.getSubwayNum() == 4) {
            id = R.drawable.linenum4;
        } else if (stationPoint.getSubwayNum() == 5) {
            id = R.drawable.linenum5;
        } else if (stationPoint.getSubwayNum() == 6) {
            id = R.drawable.linenum6;
        } else if (stationPoint.getSubwayNum() == 7) {
            id = R.drawable.linenum7;
        } else if (stationPoint.getSubwayNum() == 8) {
            id = R.drawable.linenum8;
        } else if (stationPoint.getSubwayNum() == 9) {
            id = R.drawable.linenum9;
        } else if (stationPoint.getSubwayNum() == 21) {
            id = R.drawable.linenum2122;
        } else if (stationPoint.getSubwayNum() == 22) {
            id = R.drawable.linenum2122;
        } else if (stationPoint.getSubwayNum() == 100) {
            id = R.drawable.linenum100;
        } else if (stationPoint.getSubwayNum() == 109) {
            id = R.drawable.linenum109;
        } else if (stationPoint.getSubwayNum() == 104) {
            id = R.drawable.linenum104;
        } else if (stationPoint.getSubwayNum() == 108) {
            id = R.drawable.linenum108;
        } else if (stationPoint.getSubwayNum() == 101) {
            id = R.drawable.linenum101;
        } else if (stationPoint.getSubwayNum() == 110) {
            id = R.drawable.linenum110;
        } else if (stationPoint.getSubwayNum() == 111) {
            id = R.drawable.linenum111;
        } else if (stationPoint.getSubwayNum() == 107){
            id =R.drawable.linenum107;
        }

        return id;
    }
}
