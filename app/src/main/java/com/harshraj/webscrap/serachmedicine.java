
//created by unicorn on 07/12/2019


package com.harshraj.webscrap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class serachmedicine extends AppCompatActivity implements MyTask.AsyncResponse{
EditText editText;
    ImageView button;
    AutoCompleteTextView autoCompleteTextView;
    String customURL[] ={ "https://www.drugs.com/","https://www.drugs.com/mtm/","https://www.drugs.com/cons/","https://www.drugs.com/cdi/"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serachmedicine);
        autoCompleteTextView=findViewById(R.id.edittext_search);
        button=findViewById(R.id.image_search);

        getSupportActionBar().hide();
      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().length() != 0) {
                    Intent i = new Intent(serachmedicine.this, MainActivity.class);
                    i.putExtra("harsh", autoCompleteTextView.getText().toString());
                    startActivity(i); //Do whatever you intend to do when user click on search button in keyboard.
                }
                else
                {
                    autoCompleteTextView.setError("Empty");
                    autoCompleteTextView.requestFocus();
                }
            }
        });*/


        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (autoCompleteTextView.getText().toString().length() != 0) {
                        for (int i=0;i<4;i++)
                        {
                            String x=customURL[i]+autoCompleteTextView.getText().toString().toLowerCase()+".html";
                            Log.v("plap",x);
                            MyTask task = new MyTask(serachmedicine.this);
                            Log.v("lop"," "+i);
                            task.execute(x);
                        }
                    }
                    else
                    {
                        autoCompleteTextView.setError("Empty");
                        autoCompleteTextView.requestFocus();

                    }
                }
                return true;
            }
    });
        MedicinesList medicinesList=new MedicinesList();
    autoCompleteTextView.setAdapter(new ArrayAdapter<>(serachmedicine.this,android.R.layout.simple_list_item_1,medicinesList.medicines));



    }

    @Override
    public void processFinish(String output) {
        if(!output.equals("404")) {
            Intent i = new Intent(serachmedicine.this, MainActivity.class);
            i.putExtra("harsh", output);
            i.putExtra("raj",autoCompleteTextView.getText().toString());
            Log.v("plapo", output);
            startActivity(i);
        }
    }
}
