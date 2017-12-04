package example.wherehere;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-04.
 */

public class MyListAdapter extends BaseAdapter {

    /* Adapterì— ì•„ì´í…œ View íƒ€ìž…ì— ëŒ€í•œ ìƒìˆ˜ë¥¼ ì •ì˜ */
    private static final int ITEM_VIEW_TYPE_TAB = 0;
    private static final int ITEM_VIEW_TYPE_RECOM = 1;
    private static final int ITEM_VIEW_TYPE_START = 2;
    private static final int ITEM_VIEW_TYPE_MAX = 3;

    /* ì•„ì´í…œì„ ì„¸íŠ¸ë¡œ ë‹´ê¸° ìœ„í•œ ì–´ë ˆì´ */
    private ArrayList<MyItem> mItems = new ArrayList<>();

    /* ë¦¬ìŠ¤íŠ¸ ì„ íƒ ì—¬ë¶€ */
    private int mSelectedItem = 0;
    private int TAG_UNSELECTED = 0;
    private int TAG_SELECTED = 1;


    /* Adapterì— ì‚¬ìš©ë˜ëŠ” ë°ì´í„°ì˜ ê°œìˆ˜ë¥¼ ë¦¬í„´ (í•„ìˆ˜ êµ¬í˜„) */
    @Override
    public int getCount() {
        return mItems.size();
    }

    /* ì§€ì •í•œ ìœ„ì¹˜(position)ì— ìžˆëŠ” ë°ì´í„° ë¦¬í„´ (í•„ìˆ˜ êµ¬í˜„) */
    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    /* ì§€ì •í•œ ìœ„ì¹˜(position)ì— ìžˆëŠ” ë°ì´í„°ì™€ ê´€ê³„ëœ ì•„ì´í…œ(row)ì˜ IDë¥¼ ë¦¬í„´ (í•„ìˆ˜ êµ¬í˜„) */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }

    /* position ìœ„ì¹˜ì˜ ì•„ì´í…œ íƒ€ìž… ë¦¬í„´ */
    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType() ;
    }

    public int getItemSelectedType(int position) {
        return position == mSelectedItem ? TAG_SELECTED : TAG_UNSELECTED;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            /* ê° ë¦¬ìŠ¤íŠ¸ì— ë¿Œë ¤ì¤„ ì•„ì´í…œì„ ë°›ì•„ì˜¤ëŠ”ë° mMyItem ìž¬í™œìš©
             * Data Set(MyItem)ì—ì„œ positionì— ìœ„ì¹˜í•œ ë°ì´í„° ì°¸ì¡° íšë“ */
            MyItem myItem = getItem(position);


            switch (viewType) {

                case ITEM_VIEW_TYPE_TAB:
                    convertView = inflater.inflate(R.layout.listview_tab,parent, false);
                    TextView tabText = (TextView) convertView.findViewById(R.id.tabText);
                    Button delBtn = (Button) convertView.findViewById(R.id.delButton);

                    tabText.setText(myItem.getTabStation());
                    delBtn.setOnClickListener(new Button.OnClickListener() { //ë¦¬ìŠ¤íŠ¸ì‚­ì œí•˜ê¸°
                        @Override
                        public void onClick(View arg0) {
                            mItems.remove(position);

                            notifyDataSetChanged();
                        }
                    });
                    break;
                case ITEM_VIEW_TYPE_RECOM:
                    convertView = inflater.inflate(R.layout.listview_recom,parent, false);
                    ImageView stationImg = (ImageView) convertView.findViewById(R.id.stationImg);
                    TextView stationText = (TextView) convertView.findViewById(R.id.stationText);
                    TextView timeText = (TextView) convertView.findViewById(R.id.timeText) ;

                    stationImg.setImageDrawable(myItem.getStationImg());
                    stationText.setText(myItem.getStartText());
                    timeText.setText(myItem.getTime());

                    break;
                case ITEM_VIEW_TYPE_START:
                    convertView = inflater.inflate(R.layout.listview_dialog,parent, false);
                    ImageView startImg = (ImageView) convertView.findViewById(R.id.startImg);
                    TextView startText = (TextView) convertView.findViewById(R.id.startText);

                    startImg.setImageDrawable(myItem.getStartImg());
                    startText.setText(myItem.getStartText());

                    break;
            }
        }

        /* ë¦¬ìŠ¤íŠ¸ ì„ íƒì‹œ ìƒ‰ë³€ê²½ ìœ ì§€ */
        int type = getItemSelectedType(position);
        if(type == TAG_SELECTED) {
            convertView.setBackgroundColor(Color.parseColor("#F1C40F"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#BDC3C7"));
        }

        return convertView;
    }

    public void selectItem(int position) {
        mSelectedItem = position;
        notifyDataSetChanged();
    }

    /* listview_tab ì•„ì´í…œ ì¶”ê°€ í•¨ìˆ˜ */
    public void addItem(String name) {

        MyItem mItem = new MyItem();

        /* MyItemì— ì•„ì´í…œì„ settingí•œë‹¤. */
        mItem.setType(ITEM_VIEW_TYPE_TAB);
        mItem.setTabStation(name);

        /* mItemsì— MyItemì„ ì¶”ê°€í•œë‹¤. */
        mItems.add(mItem);

    }
    /* listview_recom ì•„ì´í…œ ì¶”ê°€ í•¨ìˆ˜ */
    public void addItem(Drawable img, String name, String time) {

        MyItem mItem = new MyItem();

        /* MyItemì— ì•„ì´í…œì„ settingí•œë‹¤. */
        mItem.setType(ITEM_VIEW_TYPE_RECOM);
        mItem.setStationImg(img);
        mItem.setStartText(name);
        mItem.setTime(time);

        /* mItemsì— MyItemì„ ì¶”ê°€í•œë‹¤. */
        mItems.add(mItem);

    }
    /* listview_start ì•„ì´í…œ ì¶”ê°€ í•¨ìˆ˜ */
    public void addItem(Drawable img, String name) {

        MyItem mItem = new MyItem();

        /* MyItemì— ì•„ì´í…œì„ settingí•œë‹¤. */
        mItem.setType(ITEM_VIEW_TYPE_START);
        mItem.setStartImg(img);
        mItem.setStartText(name);

        /* mItemsì— MyItemì„ ì¶”ê°€í•œë‹¤. */
        mItems.add(mItem);

    }



}
