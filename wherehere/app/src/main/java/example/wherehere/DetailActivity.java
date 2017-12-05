package example.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by user on 2017-12-04.
 */

public class DetailActivity extends Activity {

    private ListView mListView;
    private StationPoint finalStart;
    private StationPoint finalEnd;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = new Intent(this.getIntent());

        finalStart = new StationPoint();
        finalEnd = new StationPoint();

        finalStart   =  (StationPoint)intent.getSerializableExtra("start");
        finalEnd   =  (StationPoint)intent.getSerializableExtra("end");


        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
        mListView = (ListView)findViewById(R.id.listView5);

        /* ì•„ì´í…œ ì¶”ê°€ ë° ì–´ëŒ‘í„° ë“±ë¡ */
        dataSetting();
    }

    private void dataSetting(){

        final MyListAdapter mMyAdapter = new MyListAdapter();

        mMyAdapter.addItem("출발역 :", finalStart.getStationName(), "dd", "dd");
        mMyAdapter.addItem("도착역 : ", finalEnd.getStationName(), "dd", "dd");

        /* ë¦¬ìŠ¤íŠ¸ë·°ì— ì–´ëŒ‘í„° ë“±ë¡ */
        mListView.setAdapter(mMyAdapter);
        // ì´ˆê¸° ì•„ë¬´ê²ƒë„ ì„ íƒ ì•ˆë˜ì–´ìžˆê²Œ í•˜ê¸°ìœ„í•´
        mMyAdapter.selectItem(-1);
    }

}
