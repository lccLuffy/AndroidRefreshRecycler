package com.lcc.refresh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcc.state_refresh_recyclerview.Recycler.NiceAdapter;
import com.lcc.state_refresh_recyclerview.Recycler.NiceViewHolder;
import com.lcc.state_refresh_recyclerview.Recycler.StateRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    StateRecyclerView stateRecyclerView;
    NiceAdapter<String> adapter;
    List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateRecyclerView = (StateRecyclerView) findViewById(R.id.stateRecyclerView);
        stateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);

        stateRecyclerView.setAdapter(adapter,true);

        data = new ArrayList<>();

        for (int i = 0; i < 15; i++)
        {
            data.add("data item  "+i);
        }

        adapter.setOnLoadMoreListener(new NiceAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData();
            }
        });
        stateRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear(true);
                adapter.initData(data);
            }
        });
        stateRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },2000);

    }

    void getData()
    {
        if(adapter.isDataEmpty())
        {
            adapter.initData(data);
        }
        else
        {
            adapter.addData(data);
        }
    }

    class Adapter extends NiceAdapter<String> {

        public Adapter(Context context) {
            super(context);
        }

        @Override
        protected NiceViewHolder onCreateNiceViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.textview,parent,false));
        }
        class Holder extends NiceViewHolder<String> {
            TextView textView;

            public Holder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
            }

            @Override
            public void onBindData(String data) {
                textView.setText(data);
            }
        }
    }
}
