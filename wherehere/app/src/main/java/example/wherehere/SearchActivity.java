package example.wherehere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONException;

/**
 * Created by user on 2017-12-04.
 */

public class SearchActivity extends NMapActivity implements View.OnClickListener{
    private ODsayService odsayService;

    NMapController mMapController = null;
    private LinearLayout MapContainer;
    private NMapView mMapView;// 지도 화면 View
    private NMapViewerResourceProvider mMapViewerResourceProvider = null; //NMapViewerResourceProvider 클래스 상속
    private NMapOverlayManager mOverlayManager; //지도 위에 표시되는 오버레이 객체를 관리한다.
    private NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener; //말풍선 오버레이 객체 생성 시 호출되는 콜백 인터페이스를 정의한다.

    private ImageSelector imageSelector;

    private double[][] totalTime;

    private ListView mListView;
    private StationPoint start1;
    private StationPoint start2;
    private RecommendStation recommendStation;
    ProcessingDialog processingDialog;

    private MyItem passItem;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        processingDialog = new ProcessingDialog(this);
        processingDialog.show();

        //API 설정 초기화
        odsayService = ODsayService.init(this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        imageSelector = new ImageSelector();

        totalTime = new double[3][2];

        MapContainer =(LinearLayout) findViewById(R.id.map_view);
        // 네이버 지도 객체 생성
        mMapView = new NMapView(this);

        Intent getIntent = new Intent(this.getIntent());
        start1 = (StationPoint) getIntent.getSerializableExtra("start1");
        start2 = (StationPoint) getIntent.getSerializableExtra("start2");
        recommendStation = (RecommendStation) getIntent.getSerializableExtra("recommend");
        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
        mListView = (ListView)findViewById(R.id.listView3);
        Button button = (Button)findViewById(R.id.detailButton);
        button.setOnClickListener(this);

        findAverageTime();
        findAverageTime2();
        findAverageTime3();

    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.detailButton:
                SelectDialog dialog = new SelectDialog(this,start1.getStationName(),start2.getStationName());
                dialog.setDialogListener(new MyDialogListener() {  // MyDialogListener ë¥¼ êµ¬í˜„
                    @Override
                    public void onPositiveClicked(MyItem myItem) {
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        if(passItem.getRecomText() == recommendStation.getRecommend1().getStationName()){
                            intent.putExtra("start",recommendStation.getRecommend1());
                        }else if(passItem.getRecomText() == recommendStation.getRecommend2().getStationName()){
                            intent.putExtra("start",recommendStation.getRecommend2());
                        }else if(passItem.getRecomText() == recommendStation.getRecommend3().getStationName()){
                            intent.putExtra("start",recommendStation.getRecommend3());
                        }
                        if(myItem.getStartText() == start1.getStationName()){
                            intent.putExtra("end", start1);
                        }else if(myItem.getStartText() == start2.getStationName()){
                            intent.putExtra("end", start2);
                        }
                        startActivity(intent);
                    }
                    @Override
                    public void onPositiveClicked(String station) {}
                });
                dialog.show();
                break;
        }
    }

    private void settingMap(){

        MapContainer.addView(mMapView);
        mMapView.setClientId(getString(R.string.CLIENT_ID)); // 클라이언트 아이디 값 설정

        // 지도 객체로부터 컨트롤러 추출
        mMapController = mMapView.getMapController();

        // 지도를 터치할 수 있도록 옵션 활성화
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        //create resource provider(오버레이 객체)
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this); //NMapViewerResourceProvider 클래스 상속
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider); //오버레이 객체를 화면에 표시하기 위하여 NMapResourceProvider 클래스를 상속받은 resourceProvider 객체를 전달한다
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener); //


        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider); //전체 POI 아이템의 개수와 NMapResourceProvider를 상속받은 클래스를 인자로 전달한다.
        poiData.beginPOIdata(2); //POI 아이템 추가를 시작한다.

        poiData.addPOIitem(Double.parseDouble(recommendStation.getRecommend1().getX()), Double.parseDouble(recommendStation.getRecommend1().getY()), recommendStation.getRecommend1().getStationName(), markerId, 0);
        poiData.addPOIitem(Double.parseDouble(recommendStation.getRecommend2().getX()), Double.parseDouble(recommendStation.getRecommend2().getY()), recommendStation.getRecommend2().getStationName(), markerId, 0);
        poiData.addPOIitem(Double.parseDouble(recommendStation.getRecommend3().getX()), Double.parseDouble(recommendStation.getRecommend3().getY()), recommendStation.getRecommend3().getStationName(), markerId, 0);//경도위도 좌표 입력해주면, 그 좌표가 표시됨

        poiData.endPOIdata(); //POI 아이템 추가를 종료한다.
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null); //POI 데이터를 인자로 전달하여 NMapPOIdataOverlay 객체를 생성한다.
        poiDataOverlay.showAllPOIdata(0); //POI 데이터가 모두 화면에 표시되도록 지도 축척 레벨 및 지도 중심을 변경한다. zoomLevel이 0이 아니면 지정한 지도 축척 레벨에서 지도 중심만 변경한다.
    }


    private void dataSetting(){

        final MyListAdapter mMyAdapter = new MyListAdapter();
        passItem = new MyItem();

        int id = imageSelector.selectImage(recommendStation.getRecommend1());
        int id2 = imageSelector.selectImage(recommendStation.getRecommend2());
        int id3 = imageSelector.selectImage(recommendStation.getRecommend3());
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id),recommendStation.getRecommend1().getStationName(),"평균소요시간 약" + recommendStation.getAverageTime1() + "분");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id2),recommendStation.getRecommend2().getStationName() , "평균소요시간 약" + recommendStation.getAverageTime2() + "분");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), id3),recommendStation.getRecommend3().getStationName() , "평균소요시간 약" + recommendStation.getAverageTime3() + "분");

        mListView.setAdapter(mMyAdapter);

        mMyAdapter.selectItem(-1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mMyAdapter.selectItem(position);
                passItem = mMyAdapter.getItem(position);
            }
        });
    }

    /********************************************recommend1*********************************************/
    private OnResultCallbackListener findAverageTotalTimeListener = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[0][1] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    recommendStation.setAverageTime1((int)((totalTime[0][0] + totalTime[0][1]) / 2));
//                    text1.setText("recommend1 : " + recommendStation.getRecommend1().getStationName() + "  (x, y)" + recommendStation.getRecommend1().getX() + ", " + recommendStation.getRecommend1().getY() + "\n" + "averageTime = " + recommendStation.getAverageTime1());

//                    findAverageTime2();
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    private OnResultCallbackListener findTotalTimeListener = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[0][0] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    odsayService.requestSearchPubTransPath(start2.getX(),start2.getY(), recommendStation.getRecommend1().getX(), recommendStation.getRecommend1().getY(),"0","","0", findAverageTotalTimeListener);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };
    private OnResultCallbackListener searchStationPointListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
//            JSONObject jsonObject = new JSONObject();
            if (api == API.SEARCH_STATION) {
                try {
//                    jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0);
                    recommendStation.getRecommend1().setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    recommendStation.getRecommend1().setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));
                    recommendStation.getRecommend1().setSubwayNum(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getInt("type"));

                    odsayService.requestSearchPubTransPath(start1.getX(),start1.getY(), recommendStation.getRecommend1().getX(), recommendStation.getRecommend1().getY(),"0","","0", findTotalTimeListener);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    /********************************************recommend2*********************************************/
    private OnResultCallbackListener findAverageTotalTimeListener2 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[1][1] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    recommendStation.setAverageTime2((int)((totalTime[1][0] + totalTime[1][1]) / 2));
//                    text2.setText("recommend2 : " + recommendStation.getRecommend2().getStationName() + "  (x, y)" + recommendStation.getRecommend2().getX() + ", " + recommendStation.getRecommend2().getY() + "\n" + "averageTime = " + recommendStation.getAverageTime2());

//                    findAverageTime3();
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    private OnResultCallbackListener findTotalTimeListener2 = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[1][0] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    odsayService.requestSearchPubTransPath(start2.getX(),start2.getY(), recommendStation.getRecommend2().getX(), recommendStation.getRecommend2().getY(),"0","","0", findAverageTotalTimeListener2);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };
    private OnResultCallbackListener searchStationPointListener2 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
//            JSONObject jsonObject = new JSONObject();
            if (api == API.SEARCH_STATION) {
                try {
//                    jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0);
                    recommendStation.getRecommend2().setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    recommendStation.getRecommend2().setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));
                    recommendStation.getRecommend2().setSubwayNum(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getInt("type"));
                    odsayService.requestSearchPubTransPath(start1.getX(),start1.getY(), recommendStation.getRecommend2().getX(), recommendStation.getRecommend2().getY(),"0","","0", findTotalTimeListener2);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    /********************************************recommend3*********************************************/
    private OnResultCallbackListener findAverageTotalTimeListener3 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[2][1] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    recommendStation.setAverageTime3((int)((totalTime[2][0] + totalTime[2][1]) / 2));
                    processingDialog.dismiss();
                    settingMap();
                    dataSetting();
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    private OnResultCallbackListener findTotalTimeListener3 = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {
                try {
                    totalTime[2][0] = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONObject("info").getInt("totalTime");
                    odsayService.requestSearchPubTransPath(start2.getX(),start2.getY(), recommendStation.getRecommend3().getX(), recommendStation.getRecommend3().getY(),"0","","0", findAverageTotalTimeListener3);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };
    private OnResultCallbackListener searchStationPointListener3 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
//            JSONObject jsonObject = new JSONObject();
            if (api == API.SEARCH_STATION) {
                try {
//                    jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0);
                    recommendStation.getRecommend3().setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    recommendStation.getRecommend3().setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));
                    recommendStation.getRecommend3().setSubwayNum(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getInt("type"));
                    odsayService.requestSearchPubTransPath(start1.getX(),start1.getY(), recommendStation.getRecommend3().getX(), recommendStation.getRecommend3().getY(),"0","","0", findTotalTimeListener3);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    /********************************************************평균소요시간계산 함수*****************************************************************/
    public void findAverageTime(){
        //api 호출
        odsayService.requestSearchStation(recommendStation.getRecommend1().getStationName(), "1000", "2", "","","", searchStationPointListener);
    }
    public void findAverageTime2(){
        //api 호출
        odsayService.requestSearchStation(recommendStation.getRecommend2().getStationName(), "1000", "2", "","","", searchStationPointListener2);
    }
    public void findAverageTime3(){
        //api 호출
        odsayService.requestSearchStation(recommendStation.getRecommend3().getStationName(), "1000", "2", "","","", searchStationPointListener3);
    }
}

