package example.wherehere;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/**
 * Created by user on 2017-12-04.
 */

public class AddDialog extends Dialog implements View.OnClickListener {

    private MyDialogListener dialogListener;
    private Context context;
    private TextView cancelTv;
    private TextView checkTv;

    private AutoCompleteTextView autoComplete;
    private AutoTexter autoTexter;

    public AddDialog(@NonNull Context context){
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        this.context = context;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }
    int getOKButtonId(){
        return R.id.DialogCheckTv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add);

        autoComplete = (AutoCompleteTextView) findViewById(R.id.addAutoText);
        autoComplete.getBackground().setColorFilter(Color.parseColor("#4C4A48"), PorterDuff.Mode.SRC_ATOP);
        autoTexter = new AutoTexter(autoComplete);
        autoTexter.autoCompleteText(context);

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
                String mark = autoComplete.getText().toString();
                dialogListener.onPositiveClicked(mark);
                dismiss();
                break;
        }
    }
}
