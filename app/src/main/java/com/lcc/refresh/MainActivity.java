package com.lcc.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    /*StateRecyclerView stateRecyclerView;
    NiceAdapter<String> adapter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        stateRecyclerView = (StateRecyclerView) findViewById(R.id.stateRecyclerView);
        stateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);

        stateRecyclerView.setAdapter(adapter,true);
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 15; i++)
        {
            data.add("data item  "+i);
        }


        adapter.showLoadMoreView();
        adapter.setOnLoadMoreListener(new NiceAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(adapter.isDataEmpty())
                {
                    adapter.initData(data);
                }
                else
                {
                    adapter.addData(data);
                }
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
                adapter.initData(data);
            }
        },2000);*/

    }

    /*class Adapter extends NiceAdapter<String> {

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
    }*/
}
