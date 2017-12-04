package example.wherehere;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by user on 2017-12-04.
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private ODsayService odsayService;

    private EditText editText1;
    private EditText editText2;

    //출발역들 정보저장 역이름, x, y좌표
    private StationPoint start1;
    private StationPoint start2;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //API 설정 초기화
        odsayService = ODsayService.init(this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        start1 = new StationPoint();
        start2 = new StationPoint();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button)findViewById(R.id.insertButton);
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("최근기록"));
        tabLayout.addTab(tabLayout.newTab().setText("즐겨찾기°"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyTabAdapter adapter = new MyTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // Set click Listener
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.insertButton:
                //editTextë‚´ìš© ê°€ì ¸ì˜´
                String getEdit1 = editText1.getText().toString();
                String getEdit2 = editText2.getText().toString();

                //ê³µë°±(ìŠ¤íŽ˜ì´ìŠ¤ë°”)ë§Œ ëˆŒëŸ¬ì„œ ë„˜ê¸°ëŠ” ê²½ìš°ë„ ì•ˆë¨
                getEdit1 = getEdit1.trim();
                getEdit2 = getEdit2.trim();

                //ë‘˜ë‹¤ ë¹ˆê°’ì´ ì—†ì„ë•Œ
                if((getEdit1.getBytes().length > 0) && (getEdit2.getBytes().length > 0)){
                    start1.setStationName(getEdit1);
                    start2.setStationName(getEdit2);
                    findRecommend();
//                    Intent intent = new Intent(AddActivity.this,SearchActivity.class);

//                    intent.putExtra("input1",String.valueOf(editText1.getText()));
//                    intent.putExtra("input2",String.valueOf(editText2.getText()));

                    //ê³„ì‚°ê²°ê³¼ ë„˜ê²¨ì¤„ê±°ë„ ì¶”ê°€í•„ìš”
                    //intent.putExtra("recommend1",ì¶”ì²œì—­[0]);
                    //intent.putExtra("recommend2",ì¶”ì²œì—­[1]);
                    //intent.putExtra("recommend3",ì¶”ì²œì—­[2]);
//                    startActivity(intent);
                }else {
                    ErrorDialog dialog = new ErrorDialog(this);
                    dialog.show();
                }
                break;
        }
    }



    private OnResultCallbackListener findMidStationListener = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            JSONArray subRouteArray = new JSONArray();
            RecommendStation recommendStation = new RecommendStation();
            int stationCount = 0, midStationCnt = 0, subCount = 0;
            int[] sectionCount = new int[30];

            //호출한 api가 맞을 경우
            if (api == API.SEARCH_PUB_TRANS_PATH) {

                try {
                    subRouteArray = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(0).getJSONArray("subPath");
                    subCount = subRouteArray.length();

                    //subroute의 개수만큼 arraylist생성하여 값 할당
                    for(int i = 0; i < subCount; i++) {
                        if(subRouteArray.getJSONObject(i).getInt("trafficType") == 1) {
                            try {
                                sectionCount[i] = Integer.parseInt(subRouteArray.getJSONObject(i).getString("stationCount"));
                            }catch(NumberFormatException nfe){
                                nfe.printStackTrace();
                            }
                            stationCount += sectionCount[i];
                        }else{
                            sectionCount[i] = 0;
                        }
                    }
                    midStationCnt = stationCount / 2;
                    for(int i = 0; i < subCount; i++){
                        if(subRouteArray.getJSONObject(i).getInt("trafficType") == 1) {
                            if (sectionCount[i] > midStationCnt) {
                                recommendStation.setMidstation(subRouteArray.getJSONObject(i).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(midStationCnt).getString("stationName"));
                                i = subCount;
                            }else{
                                midStationCnt = midStationCnt - sectionCount[i] -1;
                            }
                        }
                    }
                    //recommendStation의 midstation에 중간역 저장되어 있음
                    //여기서 추천역 골라서 recommedstation 마져 채우고 intent 사용해서 search activity로 넘겨주면 됨 ㅇㅇ
                    //넘겨야 되는게 start1, start2, recommendstation이렇게!!

                    StationPoint recommend1= new StationPoint();
                    StationPoint recommend2= new StationPoint();
                    StationPoint recommend3= new StationPoint();
                    recommend1.setStationName("정자");
                    recommend2.setStationName("미금");
                    recommend3.setStationName("수내");

                    recommendStation.setRecommend1(recommend1);
                    recommendStation.setRecommend2(recommend2);
                    recommendStation.setRecommend3(recommend3);


                    Intent intent = new Intent(AddActivity.this,SearchActivity.class);
                    intent.putExtra("start1", start1);
                    intent.putExtra("start2", start2);
                    intent.putExtra("recommend", recommendStation);

                    startActivity(intent);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
            //  stationPoint.setX("API : " + api.name() + "\n" + errorMessage);
            //  stationPoint.setY("API : " + api.name() + "\n" + errorMessage);
        }
    };


    private OnResultCallbackListener searchStationPointListener2 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            //호출한 api가 맞을 경우
            if (api == API.SEARCH_STATION) {
                try {
                    //start2 할당
                    start2.setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    start2.setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));

                    odsayService.requestSearchPubTransPath(start1.getX(),start1.getY(), start2.getX(), start2.getY(),"0","","1", findMidStationListener);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };

    private OnResultCallbackListener searchStationPointListener = new OnResultCallbackListener() {
        @Override
        //api 호출 성공
        public void onSuccess(ODsayData oDsayData, API api) {
            //호출한 api가 맞을 경우
            if (api == API.SEARCH_STATION) {
                try {
                    //start1 할당
                    start1.setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    start1.setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));
                    //start2좌표 채워야딩
                    odsayService.requestSearchStation(start2.getStationName(), "1000", "2", "", "", "", searchStationPointListener2);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onError(int i, String errorMessage, API api) {
        }
    };


    public void findRecommend(){
        //api 호출 - 첫번째 출발역
        odsayService.requestSearchStation(start1.getStationName(), "1000", "2", "","","", searchStationPointListener);
    }

}

