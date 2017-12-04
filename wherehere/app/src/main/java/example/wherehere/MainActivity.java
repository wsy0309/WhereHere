package example.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ì´ë¯¸ì§€ í´ë¦­í•˜ë©´ ì•¡í‹°ë¹„í‹° ë³€í™˜
        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.intro_image);
        iv.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setContentView(iv);

    }
}
