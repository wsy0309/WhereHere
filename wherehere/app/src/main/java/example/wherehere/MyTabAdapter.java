package example.wherehere;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by user on 2017-12-04.
 */

public class MyTabAdapter extends FragmentStatePagerAdapter {

    int _numOfTabs;

    public MyTabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MyTabFragment1 tab1 = new MyTabFragment1();
                return tab1;
            case 1:
                MyTabFragment2 tab2 = new MyTabFragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return _numOfTabs;
    }
}
