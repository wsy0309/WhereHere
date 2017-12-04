package example.wherehere;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 2017-12-04.
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText1;
    private EditText editText2;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button)findViewById(R.id.insertButton);
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("최근기록"));
        tabLayout.addTab(tabLayout.newTab().setText("즐겨찾기°"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyTabAdapter adapter = new MyTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // Set click Listener
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.insertButton:
                //editTextë‚´ìš© ê°€ì ¸ì˜´
                String getEdit1 = editText1.getText().toString();
                String getEdit2 = editText2.getText().toString();

                //ê³µë°±(ìŠ¤íŽ˜ì´ìŠ¤ë°”)ë§Œ ëˆŒëŸ¬ì„œ ë„˜ê¸°ëŠ” ê²½ìš°ë„ ì•ˆë¨
                getEdit1 = getEdit1.trim();
                getEdit2 = getEdit2.trim();

                //ë‘˜ë‹¤ ë¹ˆê°’ì´ ì—†ì„ë•Œ
                if((getEdit1.getBytes().length > 0) && (getEdit2.getBytes().length > 0)){
                    //ê³„ì‚° í›„ ë‹¤ìŒ íŽ˜ì´ì§€ë¡œ ë„˜ê¹€
                    //ì¤‘ê°„ì—­ ê³„ì‚° -> ì¶”ì²œì—­ 3ê°œ
                    Intent intent = new Intent(AddActivity.this,SearchActivity.class);
                    //ì¶œë°œì§€ ì„ íƒì„ ìœ„í•´ ìž…ë ¥ê°’ë„ ë„˜ê²¨ì¤Œ
                    intent.putExtra("input1",String.valueOf(editText1.getText()));
                    intent.putExtra("input2",String.valueOf(editText2.getText()));
                    //ê³„ì‚°ê²°ê³¼ ë„˜ê²¨ì¤„ê±°ë„ ì¶”ê°€í•„ìš”
                    //intent.putExtra("recommend1",ì¶”ì²œì—­[0]);
                    //intent.putExtra("recommend2",ì¶”ì²œì—­[1]);
                    //intent.putExtra("recommend3",ì¶”ì²œì—­[2]);
                    startActivity(intent);
                }
                else {
                    ErrorDialog dialog = new ErrorDialog(this);
                    dialog.show();
                }
                break;
        }
    }








}
