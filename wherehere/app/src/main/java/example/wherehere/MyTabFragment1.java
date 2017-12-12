package example.wherehere;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-12-04.
 */

public class MyTabFragment1 extends Fragment {

    private ListView mListView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public MyTabFragment1() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tab1, container, false);
        mListView = (ListView) view.findViewById(R.id.listView1);

        final MyListAdapter myListAdapter;
        myListAdapter = new MyListAdapter();



        preferences = this.getActivity().getSharedPreferences("RECENT_RECORD", MODE_PRIVATE);
        editor = preferences.edit();

        ArrayList<String> pref = new ArrayList<String>();
        int pref_size = 0;

        //현재 sharedpreference에 있는 값을 다 가져옴
        while(preferences.getString("key"+pref_size,"") != ""){
            pref.add(preferences.getString("key"+pref_size,""));
            pref_size++;
        }

        //sharedpreference에 값이 있으면
        if (pref_size != 0){
            //최근 4개까지만 보여줌
            if (pref_size > 4){
                for (int i = 0; i<4;i++){
                    myListAdapter.addItem(pref.get(i));
                }
            }
            else{
                for (int i = 0; i<pref_size;i++){
                    myListAdapter.addItem(pref.get(i));
                }
            }
        }
        myListAdapter.selectItem(-1);
        mListView.setAdapter(myListAdapter);


        return view;
    }

}
