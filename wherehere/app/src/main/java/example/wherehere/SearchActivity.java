package example.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by user on 2017-12-04.
 */

public class SearchActivity extends Activity implements View.OnClickListener{


    private ListView mListView;
    private String start1;
    private String start2;
    private String recom1;
    private String recom2;
    private String recom3;
    private MyItem passItem;

    @Override
    protected  void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = new Intent(this.getIntent());
        /* intentë¡œ ë„˜ì–´ì˜¨ ìž…ë ¥ê°’ */
        //ì¶œë°œì§€
        start1 = intent.getStringExtra("input1");
        start2 = intent.getStringExtra("input2");
        //ì¶”ì²œì—­
//        recom1=  intent.getStringExtra("recommend1");
//        recom2=  intent.getStringExtra("recommend2");
//        recom3=  intent.getStringExtra("recommend3");


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
                SelectDialog dialog = new SelectDialog(this,start1,start2);
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

        for (int i=1; i<4; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_icon), "Station_" + i, "Time_" + i);
        }

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
