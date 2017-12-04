package example.wherehere;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by user on 2017-12-04.
 */

public class MyTabFragment2 extends Fragment {

    public MyTabFragment2()
    {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab2, container, false);

        ListView mListView;
        final MyListAdapter myListAdapter;

        myListAdapter = new MyListAdapter();
        mListView = (ListView) view.findViewById(R.id.listView2);
        mListView.setAdapter(myListAdapter);
        myListAdapter.selectItem(-1);

        final ArrayList<String> myArrayList = new ArrayList<>();
        myArrayList.add("ì¦ê²¨ì°¾ê¸° 1");
        myArrayList.add("ì¦ê²¨ì°¾ê¸° 2");
        myArrayList.add("ì¦ê²¨ì°¾ê¸° 3");
        for (int i = 0; i < 3; i++) {
            myListAdapter.addItem(myArrayList.get(i));
        }

        //ë²„íŠ¼ë„???ì—¬ê¸°ì„œ???
        Button button = (Button)view.findViewById(R.id.addButton);
        button.setOnClickListener(new Button.OnClickListener() { //ë¦¬ìŠ¤íŠ¸ì‚­ì œí•˜ê¸°
            @Override
            public void onClick(View arg0) {
                AddDialog dialog = new AddDialog(getContext());
                dialog.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(MyItem myItem) {}
                    @Override
                    public void onPositiveClicked(String station) {
                        myListAdapter.addItem(station);
                        myListAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}