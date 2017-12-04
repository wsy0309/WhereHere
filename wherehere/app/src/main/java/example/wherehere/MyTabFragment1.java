package example.wherehere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-04.
 */

public class MyTabFragment1 extends Fragment {

    public MyTabFragment1() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tab1, container, false);

        ListView mListView;
        final MyListAdapter myListAdapter;
        myListAdapter = new MyListAdapter();

        mListView = (ListView) view.findViewById(R.id.listView1);
        myListAdapter.selectItem(-1);

        ArrayList<String> myArrayList = new ArrayList<>();
        myArrayList.add("ìµœê·¼ê¸°ë¡ 1");
        myArrayList.add("ìµœê·¼ê¸°ë¡ 2");
        myArrayList.add("ìµœê·¼ê¸°ë¡ 3");
        for (int i = 0; i < 3; i++) {
            myListAdapter.addItem(myArrayList.get(i));
        }

        mListView.setAdapter(myListAdapter);


        return view;
    }

}
