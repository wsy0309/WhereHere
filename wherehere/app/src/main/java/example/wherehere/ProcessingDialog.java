package example.wherehere;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by user on 2017-12-05.
 */

public class ProcessingDialog extends Dialog {

    private Context context;

    public ProcessingDialog(@NonNull Context context){
        super(context);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_processing);
    }
}
