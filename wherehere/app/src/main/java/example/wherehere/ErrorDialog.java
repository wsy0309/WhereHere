package example.wherehere;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 2017-12-04.
 */

public class ErrorDialog extends Dialog implements View.OnClickListener{

    private MyDialogListener dialogListener;
    private Context context;
    private TextView cancelTv;
    private TextView checkTv;


    public ErrorDialog(@NonNull Context context){
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        this.context = context;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_error);
        cancelTv = (TextView) findViewById(R.id.DialogCancelTv);
        checkTv = (TextView) findViewById(R.id.DialogCheckTv);

        cancelTv.setOnClickListener(this);
        checkTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DialogCancelTv:
                cancel();
                break;
            case R.id.DialogCheckTv:
                dismiss();
                break;
        }
    }

}
