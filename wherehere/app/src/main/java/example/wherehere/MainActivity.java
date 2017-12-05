package example.wherehere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {
    private String mid;
    private ArrayList<String> recom;

    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ì´ë¯¸ì§€ í´ë¦­í•˜ë©´ ì•¡í‹°ë¹„í‹° ë³€í™˜
        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.intro_image);
//        iv.setOnClickListener(new View.OnClickListener(){
//            public  void onClick(View v){
//                Intent intent = new Intent(MainActivity.this,AddActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        setContentView(iv);

        try {
            int i;
            InputStream is = getResources().openRawResource(R.raw.recom);
            InputStreamReader stream = new InputStreamReader(is, "utf-8");
            BufferedReader buffer = new BufferedReader(stream);
            String read;
            while ((read = buffer.readLine()) != null) {
                recom = new ArrayList<String>();
                String[] bbb = read.split("\\/");
                mid = bbb[0];
                recom.add(bbb[1]);
                recom.add(bbb[2]);
                recom.add(bbb[3]);
                setStringArrayPref(getApplicationContext(), mid, recom);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        startActivity(intent);
        finish();
    }

    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = context.getSharedPreferences("RECOMMEND",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(values);
        editor.putStringSet(key, set);
        editor.commit();
    }
}
