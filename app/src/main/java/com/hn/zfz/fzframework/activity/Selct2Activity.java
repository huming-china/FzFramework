package com.hn.zfz.fzframework.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hn.zfz.bean.FenLei;
import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.adapter.SelectAdapter;
import com.hn.zfz.fzframework.base.BaseActivity;
import com.hn.zfz.xml.PullXmlUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class Selct2Activity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private SelectAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select1);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        String id= getIntent().getExtras().getString("id");
        get(id);
    }
    private void get(String id){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("daleiid",id)
                .add("xiaoleiid", "0")
                .add("gongsiming","百川考试软件")
                .build();
        String url="http://www.bc150.com/pz.asmx/kecheng";
        Request requestPost = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        mOkHttpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("失败");
            }

            //
            @Override
            public void onResponse(Response response) throws IOException {
                String str=response.body().string();
                try {
                    final ArrayList<FenLei> fenleis= PullXmlUtil.pullKeChengFeiLei(str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setAdapter(mAdapter = new SelectAdapter(fenleis,true));
                        }
                    });
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
