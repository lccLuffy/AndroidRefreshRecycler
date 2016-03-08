package com.lcc.state_refresh_recyclerview.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lcc.state_refresh_recyclerview.R;

/**
 * Created by lcc_luffy on 2016/1/11.
 */
public class LoadMoreFooter implements NiceAdapter.OnDataCountChangeListener,NiceAdapter.ItemView{

    public static final int IS_SHOW_MORE_VIEW = 0;
    public static final int IS_SHOW_NO_MORE_VIEW = 1;
    public static final int IS_SHOW_ERROR_VIEW = 2;

    private boolean enableReload = true;

    int dataCount;
    private OnLoadMoreListener onLoadMoreListener;
    private View.OnClickListener onErrorClickListener;

    private int state = IS_SHOW_MORE_VIEW;

    private FooterView footer;

    public LoadMoreFooter(Context context)
    {
        footer = new FooterView(context);
        footer.hide();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnErrorViewClickListener(View.OnClickListener onErrorViewClickListener) {
        onErrorClickListener = onErrorViewClickListener;
    }

    public void showErrorView() {
        footer.showErrorView();
    }

    public void showNoMoreView() {
        footer.showNoMoreView();
    }

    public void showLoadMoreView() {
        footer.showMoreView();
    }


    public void onMoreViewShowed()
    {
        if(onLoadMoreListener != null && dataCount != 0)
            onLoadMoreListener.onLoadMore();
    }

    public void onErrorViewShowed()
    {
        //// TODO: 2016/3/8  onErrorViewShowedListener
    }

    public void onNoMoreViewShowed()
    {
        //// TODO: 2016/3/8 onNoMoreViewShowedListener
    }

    @Override
    public void OnDataCountChange(int beforeDataCount, int afterDataCount, int allCount) {
        dataCount = afterDataCount;
        if(afterDataCount == 0)
            footer.hide();
        else
            footer.show();
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return footer.onCreateView(parent);
    }

    @Override
    public void onBindViewHolder(int realPosition) {
        footer.onBindViewHolder(realPosition);
    }

    private class FooterView
    {
        private FrameLayout container;
        private View loadMoreView;
        private View noMoreView;
        private View errorView;

        FooterView(Context context)
        {
            loadMoreView = LayoutInflater.from(context).inflate(R.layout.footer_progress_view,container,false);
            noMoreView = LayoutInflater.from(context).inflate(R.layout.footer_no_more_view,container,false);
            errorView = LayoutInflater.from(context).inflate(R.layout.footer_error_view,container,false);

            container.addView(loadMoreView);
            container.addView(noMoreView);
            container.addView(errorView);


            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onErrorClickListener != null) {
                        onErrorClickListener.onClick(v);
                    }
                    if(enableReload)
                    {
                        showMoreView();
                        onMoreViewShowed();
                    }
                }
            });

            container = new FrameLayout(context);
            container.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            showMoreView();
        }
        public View onCreateView(ViewGroup parent) {
            return container;
        }

        public void onBindViewHolder(int realPosition) {
            switch (state)
            {
                case IS_SHOW_MORE_VIEW:
                    onMoreViewShowed();
                    break;
                case IS_SHOW_ERROR_VIEW:
                    onErrorViewShowed();
                    break;
                case IS_SHOW_NO_MORE_VIEW:
                    onNoMoreViewShowed();
                    break;
            }
        }

        private void showView(View view)
        {
            show();
            for (int i = 0; i < container.getChildCount(); i++)
            {
                if (container.getChildAt(i) == view) view.setVisibility(View.VISIBLE);
                else container.getChildAt(i).setVisibility(View.GONE);
            }
        }

        public void showErrorView(){
            showView(errorView);
            state = IS_SHOW_ERROR_VIEW;
        }
        public void showMoreView(){
            showView(loadMoreView);
            state = IS_SHOW_MORE_VIEW;
        }
        public void showNoMoreView(){
            showView(noMoreView);
            state = IS_SHOW_NO_MORE_VIEW;
        }

        public void hide()
        {
            if(container.getVisibility() != View.GONE)
                container.setVisibility(View.GONE);
        }

        public void show() {
            if(container.getVisibility() != View.VISIBLE)
                container.setVisibility(View.VISIBLE);
        }
    }
    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }
}
