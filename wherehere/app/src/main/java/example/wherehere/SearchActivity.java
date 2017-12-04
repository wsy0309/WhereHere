package example.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.odsay.odsayandroidsdk.ODsayService;

/**
 * Created by user on 2017-12-04.
 */

public class SearchActivity extends Activity implements View.OnClickListener{
    private ODsayService odsayService;

    private ListView mListView;
    private StationPoint start1;
    private StationPoint start2;
    private RecommendStation recommendStation;

    private MyItem passItem;

    @Override
    protected  void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = new Intent(this.getIntent());

        start1 = (StationPoint) intent.getSerializableExtra("start1");
        start2 = (StationPoint) intent.getSerializableExtra("start2");
        recommendStation = (RecommendStation) intent.getSerializableExtra("recommend");



        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
        mListView = (ListView)findViewById(R.id.listView3);
        Button button = (Button)findViewById(R.id.detailButton);
        button.setOnClickListener(this);
        /* ì•„ì´í…œ ì¶”ê°€ ë° ì–´ëŒ‘í„° ë“±ë¡ */
        dataSetting();


    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.detailButton:
                SelectDialog dialog = new SelectDialog(this,start1.getStationName(),start2.getStationName());
                dialog.setDialogListener(new MyDialogListener() {  // MyDialogListener ë¥¼ êµ¬í˜„
                    @Override
                    public void onPositiveClicked(MyItem myItem) {
                        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                        intent.putExtra("final_start",myItem);
                        intent.putExtra("final_end",passItem);
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

        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon), "Station_" + recommendStation.getRecommend1().getStationName(),"Time_");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon), "Station_" + recommendStation.getRecommend2().getStationName() , "Time_");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon), "Station_" + recommendStation.getRecommend3().getStationName() , "Time_");


        /* ë¦¬ìŠ¤íŠ¸ë·°ì— ì–´ëŒ‘í„° ë“±ë¡ */
        mListView.setAdapter(mMyAdapter);
        // ì´ˆê¸° ì•„ë¬´ê²ƒë„ ì„ íƒ ì•ˆë˜ì–´ìžˆê²Œ í•˜ê¸°ìœ„í•´
        mMyAdapter.selectItem(-1);
        // Get selected item and update its background
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mMyAdapter.selectItem(position);
                passItem = mMyAdapter.getItem(position);
            }
        });
    }




}
