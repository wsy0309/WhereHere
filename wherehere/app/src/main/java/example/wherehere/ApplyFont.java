package example.wherehere;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by user on 2017-12-08.
 */

public class ApplyFont extends Application {
    @Override public void onCreate() {

        super.onCreate();

        // 폰트 정의

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "BMHANNA_11yrs_ttf.ttf"));

    }
}
