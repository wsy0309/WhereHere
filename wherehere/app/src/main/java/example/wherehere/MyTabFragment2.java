package example.wherehere;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-12-04.
 */

public class MyTabFragment2 extends Fragment {

    private ListView mListView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<String> pref;
    private int pref_size;

    public MyTabFragment2()
    {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tab2, container, false);

        ListView mListView;
        final MyListAdapter myListAdapter;

        myListAdapter = new MyListAdapter();
        mListView = (ListView) view.findViewById(R.id.listView2);

        preferences = this.getActivity().getSharedPreferences("BOOKMARK", MODE_PRIVATE);
        editor = preferences.edit();

        pref = new ArrayList<String>();
        pref_size = 0;

        //현재 sharedpreference에 있는 값을 다 가져옴
        while(preferences.getString("key"+pref_size,"") != ""){
            pref.add(preferences.getString("key"+pref_size,""));
            pref_size++;
        }

        //sharedpreference에 값이 있으면
        if (pref_size != 0){
            for (int i = 0; i<pref_size;i++){
                myListAdapter.addItem(pref.get(i));
            }
        }
        mListView.setAdapter(myListAdapter);
        myListAdapter.selectItem(-1);

        //ë²„íŠ¼ë„???ì—¬ê¸°ì„œ???
        Button button = (Button)view.findViewById(R.id.addButton);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final AddDialog dialog = new AddDialog(getContext());
                dialog.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(MyItem myItem) {}
                    @Override
                    public void onPositiveClicked(String station) {
                        if(station.equals("")){
                            Toast toast = Toast.makeText(getContext(), "역을 입력해주세요.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 0, 300);
                            toast.show();
                        }else{
                            myListAdapter.addItem(station);
                            myListAdapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}