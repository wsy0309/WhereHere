package example.wherehere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by user on 2017-12-04.
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private ODsayService odsayService;

    private EditText editText1;
    private EditText editText2;
    ArrayList<String> res = new ArrayList<String>();

    //출발역들 정보저장 역이름, x, y좌표
    private StationPoint start1;
    private StationPoint start2;

    private AutoCompleteTextView autoComplete1;
    private AutoCompleteTextView autoComplete2;
    private  AutoTexter autoTexter1;
    private  AutoTexter autoTexter2;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
        autoComplete1 = (AutoCompleteTextView) findViewById(R.id.autoText1);
        autoComplete2 = (AutoCompleteTextView) findViewById(R.id.autoText2);

        autoComplete1.getBackground().setColorFilter(Color.parseColor("#4C4A48"), PorterDuff.Mode.SRC_ATOP);
        autoComplete2.getBackground().setColorFilter(Color.parseColor("#4C4A48"), PorterDuff.Mode.SRC_ATOP);

        autoTexter1 = new AutoTexter(autoComplete1);
        autoTexter2 = new AutoTexter(autoComplete2);
        autoTexter1.autoCompleteText(this);
        autoTexter2.autoCompleteText(this);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackground(getResources().getDrawable(R.drawable.pressed1_tab));
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyTabAdapter adapter = new MyTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){
                    tabLayout.setBackground(getResources().getDrawable(R.drawable.pressed1_tab));
                }
                else if (tab.getPosition() == 1){
                    tabLayout.setBackground(getResources().getDrawable(R.drawable.pressed2_tab));
                }

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
                //editText내용 가져옴
                String getEdit1 = autoComplete1.getText().toString();
                String getEdit2 = autoComplete2.getText().toString();

                //공백(스페이스바)만 눌러서 넘기는 경우도 안됨
                getEdit1 = getEdit1.trim();
                getEdit2 = getEdit2.trim();

                //둘다 빈값이 없을때
                if((getEdit1.getBytes().length > 0) && (getEdit2.getBytes().length > 0)){

                    preferences = getSharedPreferences("RECENT_RECORD", MODE_PRIVATE);
                    editor = preferences.edit();


                    ArrayList<String> pref = new ArrayList<String>();
                    int pref_size = 0;

                    //현재 sharedpreference에 있는 값을 다 가져와서 지움
                    while(preferences.getString("key"+pref_size,"") != ""){
                        pref.add(preferences.getString("key"+pref_size,""));
                        pref_size++;
                    }
                    //만약에 editText1값이 sharedpreference에 없으면
                    if (!pref.contains(autoComplete1.getText().toString())){
                        //만약에 editText2값도 sharedpreference에 없으면
                        if (!pref.contains(autoComplete2.getText().toString())) {
                            editor.putString("key0", autoComplete1.getText().toString());
                            editor.putString("key1", autoComplete2.getText().toString());
                            //key 3 부터 채우기
                            for (int i = 2; i < (pref_size + 2); i++) {
                                editor.putString("key" + i, pref.get(i - 2));
                            }
                        }
                        //editText1값만 sharedpreference에 없으면
                        else {
                            editor.putString("key0",autoComplete1.getText().toString());
                            for(int i = 1; i < (pref_size + 1);i++){
                                editor.putString("key"+i,pref.get(i-1));
                            }
                        }
                    }
                    editor.commit();




                    start1.setStationName(getEdit1);
                    start2.setStationName(getEdit2);
                    findRecommend();

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

                    res = getStringArrayPref(getApplicationContext(), recommendStation.getMidstation());

                    StationPoint recommend1= new StationPoint();
                    StationPoint recommend2= new StationPoint();
                    StationPoint recommend3= new StationPoint();

                    recommend1.setStationName(res.get(0));
                    recommend2.setStationName(res.get(1));
                    recommend3.setStationName(res.get(2));

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

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("RECOMMEND", Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(key, null);
        return new ArrayList<String>(set);
    }

    private OnResultCallbackListener searchStationPointListener2 = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            //호출한 api가 맞을 경우
            if (api == API.SEARCH_STATION) {
                try {
                    //start2 할당
                    start2.setX(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("x")));
                    start2.setY(Double.toString(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getDouble("y")));
                    start2.setSubwayNum(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getInt("type"));

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
                    start1.setSubwayNum(oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(0).getInt("type"));
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

    @Override protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}

