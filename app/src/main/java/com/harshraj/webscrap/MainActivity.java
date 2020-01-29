//@created by------>>>> unicorn09 on 08/12/2019*
package com.harshraj.webscrap;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private Document mBlogDocument;
    private String ptag=" ";
    String p,medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        p=i.getStringExtra("harsh");
        medicine=i.getStringExtra("raj");
        Log.e("plpl",p);
        getSupportActionBar().setTitle(medicine.toUpperCase());
        new Description().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, serachmedicine.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Searching 142154 Medicines");
            mProgressDialog.setMessage("Please Wait....");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                mBlogDocument = Jsoup.connect(p).get();

                Log.e("phrk",mBlogDocument.toString());
                mBlogTitleList.add(medicine.toUpperCase());
                Elements mElementDataSize = mBlogDocument.select("ul.nav-tabs.nav-tabs-collapse.vmig,p,h2,ul");
                for (Element elem : mElementDataSize) {

                    if(elem.equals(elem.selectFirst("p"))||elem.equals(elem.selectFirst("h2")))
                    {
                        if(elem.equals(elem.selectFirst("h2")))
                        {
                            mBlogTitleList.add(elem.selectFirst("h2").text());
                            mAuthorNameList.add(ptag);
                            ptag=" ";

                        }
                        else if(elem.equals(elem.selectFirst("p")))
                        ptag=ptag+"\n\n"+elem.selectFirst("p").text();

                    }

                }
            } catch (IOException e) {
              Log.e("error",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);

            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mBlogTitleList, mAuthorNameList);

            Log.d("pkpkpkpk",mBlogTitleList.toString());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mDataAdapter);
            mProgressDialog.dismiss();
        }
    }
}