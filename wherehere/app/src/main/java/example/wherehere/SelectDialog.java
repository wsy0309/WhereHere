package example.wherehere;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by user on 2017-12-04.
 */

public class SelectDialog extends Dialog implements View.OnClickListener {

    private MyDialogListener dialogListener;
    private Context context;
    private TextView cancelTv;
    private TextView checkTv;

    private ListView mListView;
    private StationPoint start1;
    private StationPoint start2;
    private MyItem passItem;

    private ImageSelector imageSelector;


    public SelectDialog(@NonNull Context context, StationPoint start1, StationPoint start2){
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        this.context = context;
        this.start1 = start1;
        this.start2 = start2;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        cancelTv = (TextView) findViewById(R.id.DialogCancelTv);
        checkTv = (TextView) findViewById(R.id.DialogCheckTv);

        /* ìœ„ì ¯ê³¼ ë©¤ë²„ë³€ìˆ˜ ì°¸ì¡° íšë“ */
        mListView = (ListView)findViewById(R.id.listView4);
        Button button = (Button)findViewById(R.id.detailButton);

        /* ì•„ì´í…œ ì¶”ê°€ ë° ì–´ëŒ‘í„° ë“±ë¡ */
        dataSetting();

        cancelTv.setOnClickListener(this);
        checkTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final MyListAdapter mMyAdapter = new MyListAdapter();

        switch (v.getId()){
            case R.id.DialogCancelTv:
                cancel();
                break;
            case R.id.DialogCheckTv:

                dialogListener.onPositiveClicked(passItem);
                dismiss();
                break;
        }
    }

    private void dataSetting(){
        imageSelector = new ImageSelector();
        int id;

        final MyListAdapter mMyAdapter = new MyListAdapter();
        passItem = new MyItem();

        id = imageSelector.selectImage(start1);
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), id), start1.getStationName());
        id = imageSelector.selectImage(start2);
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), id), start2.getStationName());

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
