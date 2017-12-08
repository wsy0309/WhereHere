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

import static example.wherehere.R.id.stationImg;
import static example.wherehere.R.id.stationText;
import static example.wherehere.R.id.timeText;

/**
 * Created by user on 2017-12-04.
 */

public class MyListAdapter extends BaseAdapter {

    /* Adapter에 아이템 View 타입에 대한 상수를 정의 */
    private static final int ITEM_VIEW_TYPE_TAB = 0;
    private static final int ITEM_VIEW_TYPE_RECOM = 1;
    private static final int ITEM_VIEW_TYPE_START = 2;
    private static final int ITEM_VIEW_TYPE_DETAIL_1 = 3;
    private static final int ITEM_VIEW_TYPE_DETAIL_2 = 4;
    private static final int ITEM_VIEW_TYPE_MAX = 5;

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();

    /* 리스트 선택 여부 */
    private int mSelectedItem = 0;
    private int TAG_UNSELECTED = 0;
    private int TAG_SELECTED = 1;


    /* Adapter에 사용되는 데이터의 개수를 리턴 (필수 구현) */
    @Override
    public int getCount() {
        return mItems.size();
    }

    /* 지정한 위치(position)에 있는 데이터 리턴 (필수 구현) */
    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    /* 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴 (필수 구현) */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }

    /* position 위치의 아이템 타입 리턴 */
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

            /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용
             * Data Set(MyItem)에서 position에 위치한 데이터 참조 획득 */
            MyItem myItem = getItem(position);


            switch (viewType) {

                case ITEM_VIEW_TYPE_TAB:
                    convertView = inflater.inflate(R.layout.listview_tab,parent, false);
                    TextView tabText = (TextView) convertView.findViewById(R.id.tabText);
                    Button delBtn = (Button) convertView.findViewById(R.id.delButton);

                    tabText.setText(myItem.getTabStation());
                    delBtn.setOnClickListener(new Button.OnClickListener() { //리스트삭제하기
                        @Override
                        public void onClick(View arg0) {
                            mItems.remove(position);

                            notifyDataSetChanged();
                        }
                    });
                    break;
                case ITEM_VIEW_TYPE_RECOM:
                    convertView = inflater.inflate(R.layout.listview_recom,parent, false);
                    ImageView recomImg = (ImageView) convertView.findViewById(stationImg);
                    TextView recomText = (TextView) convertView.findViewById(stationText);
                    TextView recomTime = (TextView) convertView.findViewById(timeText) ;

                    recomImg.setImageDrawable(myItem.getRecomImg());
                    recomText.setText(myItem.getRecomText());
                    recomTime.setText(myItem.getRecomTime());
                    break;
                case ITEM_VIEW_TYPE_START:
                    convertView = inflater.inflate(R.layout.listview_dialog,parent, false);
                    ImageView startImg = (ImageView) convertView.findViewById(R.id.startImg);
                    TextView startText = (TextView) convertView.findViewById(R.id.startText);

                    startImg.setImageDrawable(myItem.getStartImg());
                    startText.setText(myItem.getStartText());
                    break;
                case ITEM_VIEW_TYPE_DETAIL_1:
                    convertView = inflater.inflate(R.layout.listview_detail1,parent, false);
                    TextView strtEnd = (TextView) convertView.findViewById(R.id.strtEndText);
                    TextView stationText = (TextView) convertView.findViewById(R.id.stationText);
                    TextView timeText = (TextView) convertView.findViewById(R.id.timeText);
                    TextView costText = (TextView) convertView.findViewById(R.id.costText);

                    strtEnd.setText(myItem.getStrtEnd());
                    stationText.setText(myItem.getStrtEndStation());
                    timeText.setText(myItem.getStrtEndTime());
                    costText.setText(myItem.getStrtEndCost());
                    break;
                case ITEM_VIEW_TYPE_DETAIL_2:
                    convertView = inflater.inflate(R.layout.listview_detail2,parent, false);
                    ImageView typeImg = (ImageView) convertView.findViewById(R.id.typeImg);
                    TextView typeNum = (TextView) convertView.findViewById(R.id.numText);
                    TextView subStart = (TextView) convertView.findViewById(R.id.subStartText);
                    TextView subEnd = (TextView) convertView.findViewById(R.id.subEndText);
                    TextView subTime = (TextView) convertView.findViewById(R.id.subTimeText);
                    TextView subDist = (TextView) convertView.findViewById(R.id.subNum);

                    typeImg.setImageDrawable(myItem.getTypeImg());
                    typeNum.setText(myItem.getTypeNum());
                    subStart.setText(myItem.getSubStart());
                    subEnd.setText(myItem.getSubEnd());
                    subTime.setText(myItem.getSubTime());
                    subDist.setText(myItem.getSubDist());

                    break;
            }
        }

        /* 리스트 선택시 색변경 유지 */
        int type = getItemSelectedType(position);
        if(type == TAG_SELECTED) {
            convertView.setBackgroundColor(Color.parseColor("#bdc3c7"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#00000000"));
        }

        return convertView;
    }

    public void selectItem(int position) {
        mSelectedItem = position;
        notifyDataSetChanged();
    }

    /* listview_tab 아이템 추가 함수 */
    public void addItem(String name) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setType(ITEM_VIEW_TYPE_TAB);
        mItem.setTabStation(name);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
    /* listview_recom 아이템 추가 함수 */
    public void addItem(Drawable img, String name, String time) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setType(ITEM_VIEW_TYPE_RECOM);
        mItem.setRecomImg(img);
        mItem.setRecomText(name);
        mItem.setRecomTime(time);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
    /* listview_start 아이템 추가 함수 */
    public void addItem(Drawable img, String name) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setType(ITEM_VIEW_TYPE_START);
        mItem.setStartImg(img);
        mItem.setStartText(name);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }

    public void addItem(String str1,String str2, String str3, String str4){

        MyItem mItem = new MyItem();

        mItem.setType(ITEM_VIEW_TYPE_DETAIL_1);
        mItem.setStrtEnd(str1);
        mItem.setStrtEndStation(str2);
        mItem.setStrtEndTime(str3);
        mItem.setStrtEndCost(str4);

        mItems.add(mItem);
    }

    public void addItem(Drawable img, String str1,String str2, String str3, String str4, String str5){

        MyItem mItem = new MyItem();

        mItem.setType(ITEM_VIEW_TYPE_DETAIL_2);
        mItem.setTypeImg(img);
        mItem.setTypeNum(str2);
        mItem.setSubStart(str1);
        mItem.setSubEnd(str3);
        mItem.setSubTime(str4);
        mItem.setSubDist(str5);

        mItems.add(mItem);
    }


}
