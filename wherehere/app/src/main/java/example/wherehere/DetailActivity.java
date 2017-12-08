package example.wherehere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-04.
 */

public class DetailActivity extends Activity implements StartProgress{
    private ODsayService odsayService;

    private ListView mListView;
    private StationPoint start;
    private StationPoint end;

    private Route route;
    private ArrayList<DetailRoute> detailRoute;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressON("Loading...");

        //API 설정 초기화
        odsayService = ODsayService.init(this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        route = new Route();
        detailRoute = new ArrayList<DetailRoute>();
        Intent intent = new Intent(this.getIntent());
        start = new StationPoint();
        end = new StationPoint();

        start   =  (StationPoint)intent.getSerializableExtra("start");
        end   =  (StationPoint)intent.getSerializableExtra("end");

        route.setStartStation(start.getStationName());
        route.setEndStation(end.getStationName());

        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
        mListView = (ListView)findViewById(R.id.listView5);

        findRoute();
//        dataSetting();
    }

    private void dataSetting(){

        final MyListAdapter mMyAdapter = new MyListAdapter();

        mMyAdapter.addItem(start.getStationName(), end.getStationName(), Integer.toString(route.getTotalTime()), Integer.toString(route.getPayment()));

        DetailRoute tmp;
        int len = route.getDetailRoute().size();
        int id = 0;
        for(int i = 0; i <len;i++){
            tmp = route.getDetailRoute().get(i);
            if(tmp.getTrafficType() == 1) { //지하철
                id = R.drawable.subway_icon;
                mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id), tmp.getStartName() + "역", tmp.getSubwayID() + "승차 후, ", tmp.getEndName()+ "역 하차", "약 " + tmp.getSectionTime() + "분", tmp.getStationCount() + "개 역");
            }else if(tmp.getTrafficType() == 2){ //버스
                id = R.drawable.bus_icon;
                mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id), tmp.getStartName() + "정류장", tmp.getBusID() + "승차 후, ", tmp.getEndName() + "정류장 하차", "약 " + tmp.getSectionTime() + "분", tmp.getStationCount() + "개 정류장");

            }else if(tmp.getTrafficType() == 3){ // 도보
                id = R.drawable.walk_icon;
                if(i == 0){
                    mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id), "",route.getStartStation() + "에서 ", route.getDetailRoute().get(i+1).getStartName() + "까지", "약 " + tmp.getSectionTime() + "분", "약 " +Integer.toString((int)tmp.getDistance()) + "m 걷기");

                }else if(i == len-1){
                    mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id), "",route.getDetailRoute().get(i-1).getEndName() + "에서 ",route.getEndStation() + "까지", "약 " + tmp.getSectionTime() + "분" , "약 " + Integer.toString((int)tmp.getDistance()) + "m 걷기");

                }else{
                    mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id), "",route.getDetailRoute().get(i-1).getEndName() + "에서", route.getDetailRoute().get(i+1).getStartName() + "까지", "약 " + tmp.getSectionTime() + "분", "약 " + Double.toString(tmp.getDistance()) + "m 걷기");

                }
            }
        }
        mListView.setAdapter(mMyAdapter);

        mMyAdapter.selectItem(-1);
    }

    private OnResultCallbackListener findRouteListener = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            JSONArray subRouteArray = new JSONArray();
            JSONObject infoObject = new JSONObject();
            //호출한 api가 맞을 경우
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    subRouteArray = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONArray("subPath");
                    infoObject = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info");
                    int error = 0;
                    int subCount = subRouteArray.length();
                    DetailRoute tempRoute = new DetailRoute();

                    //subroute의 개수만큼 arraylist생성하여 값 할당
                    for(int i = 0; i < subCount; i++){
                        tempRoute = new DetailRoute();
                        if(subRouteArray.getJSONObject(i).getInt("trafficType") == 3){//도보
                            if(subRouteArray.getJSONObject(i).getInt("distance") <= 1){//distance = 0 무시
                                if(subRouteArray.getJSONObject(i).getInt("distance") == 1){
                                    error++;
                                }
                                //ignore
                                continue;
                            }
                            else{//도보면서 distance = 0 이 아닐경우
                                tempRoute.setTrafficType(subRouteArray.getJSONObject(i).getInt("trafficType"));
                                tempRoute.setSectionTime(subRouteArray.getJSONObject(i).getString("sectionTime"));
                                tempRoute.setDistance(subRouteArray.getJSONObject(i).getDouble("distance"));
                                //이전 subroute의 도착지에서 다음 subroute 출발지까지 걷는거임!
                                detailRoute.add(tempRoute);
                            }
                        }else{//지하철 & 버스 공통
                            tempRoute.setTrafficType(subRouteArray.getJSONObject(i).getInt("trafficType"));
                            tempRoute.setStartName(subRouteArray.getJSONObject(i).getString("startName"));
                            tempRoute.setEndName(subRouteArray.getJSONObject(i).getString("endName"));
                            tempRoute.setSectionTime(subRouteArray.getJSONObject(i).getString("sectionTime"));
                            tempRoute.setStationCount(subRouteArray.getJSONObject(i).getString("stationCount"));

                            if(tempRoute.getTrafficType() == 1){//지하철
                                tempRoute.setSubwayID(subRouteArray.getJSONObject(i).getJSONArray("lane").getJSONObject(0).getString("name"));
                            }else if(tempRoute.getTrafficType() == 2){//버스
                                tempRoute.setBusID(subRouteArray.getJSONObject(i).getJSONArray("lane").getJSONObject(0).getString("busNo"));
                            }
                            detailRoute.add(tempRoute);
                        }

                    }
                    route.setDetailRoute(detailRoute);
                    route.setTotalTime(infoObject.getInt("totalTime") - error);
                    route.setPayment(infoObject.getInt("payment"));

                    progressOFF();

                    dataSetting();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
//            text1.setText("API : " + api.name() + "\n" + errorMessage);
            // y = "API : " + api.name() + "\n" + errorMessage;
        }
    };


    public void findRoute(){
        //api 호출
        if(start.getStationName() == end.getStationName()){
        }else {
            odsayService.requestSearchPubTransPath(start.getX(), start.getY(), end.getX(), end.getY(), "0", "", "0", findRouteListener);
        }
    }

    @Override
    public void progressON() {
        ProgressDialog.getInstance().progressON(this);
    }

    @Override
    public void progressON(String message) {
        ProgressDialog.getInstance().progressON(this, message);
    }
    @Override
    public void progressOFF() {
        ProgressDialog.getInstance().progressOFF();
    }

    @Override protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
