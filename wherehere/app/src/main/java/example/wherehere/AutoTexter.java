package example.wherehere;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 2017-12-07.
 */

public class AutoTexter implements TextWatcher{

    private AutoCompleteTextView autoComplete;
    private ArrayList<String> autotext = new ArrayList<String>();

    public AutoTexter (AutoCompleteTextView auto){
        this.autoComplete = auto;
    }

    public void autoCompleteText(Context context){

        try {
            int i;
            InputStream is = context.getResources().openRawResource(R.raw.recom);
            InputStreamReader stream = new InputStreamReader(is, "utf-8");
            BufferedReader buffer = new BufferedReader(stream);
            String read;
            while ((read = buffer.readLine()) != null) {
                String[] bbb = read.split("\\/");
                autotext.add(bbb[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        autoComplete.addTextChangedListener(this);
        autoComplete.setAdapter(new ArrayAdapter<String>(
                context,
                android.R.layout.simple_dropdown_item_1line,
                autotext));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
