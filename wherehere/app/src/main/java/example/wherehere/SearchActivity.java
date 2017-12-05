package example.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONException;

/**
 * Created by user on 2017-12-04.
 */

public class SearchActivity extends Activity implements View.OnClickListener{
    private ODsayService odsayService;

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

        totalTime = new double[3][2];

        Intent getIntent = new Intent(this.getIntent());
        start1 = (StationPoint) getIntent.getSerializableExtra("start1");
        start2 = (StationPoint) getIntent.getSerializableExtra("start2");
        recommendStation = (RecommendStation) getIntent.getSerializableExtra("recommend");
        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
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

    private void dataSetting(){

            final MyListAdapter mMyAdapter = new MyListAdapter();
            passItem = new MyItem();

            mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon),recommendStation.getRecommend1().getStationName(),"평균소요시간 약" + recommendStation.getAverageTime1() + "분");
            mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon),recommendStation.getRecommend2().getStationName() , "평균소요시간 약" + recommendStation.getAverageTime2() + "분");
            mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon),recommendStation.getRecommend3().getStationName() , "평균소요시간 약" + recommendStation.getAverageTime3() + "분");

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
                    dataSetting();

                    //intent!!!!!!!!!!!!!!1- 실제로는 선택한것 넣어야 함 recommend저거
//                    Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
//                    intent.putExtra("start",start1);
//                    intent.putExtra("end",recommendStation.getRecommend2());
//                    startActivity(intent);

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
