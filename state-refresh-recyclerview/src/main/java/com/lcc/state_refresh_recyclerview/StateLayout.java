package com.lcc.state_refresh_recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by lcc_luffy on 2016/1/30.
 */
public class StateLayout extends FrameLayout{
    protected View contentView;
    protected View emptyView;
    protected View errorView;
    protected View progressView;

    protected TextView emptyTextView;
    protected TextView errorTextView;
    protected TextView progressTextView;


    protected Button errorButton;
    protected Button emptyButton;

    public StateLayout(Context context) {
        this(context,null);
    }


    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        parseAttrs(context,attrs);

        currentShowingView = contentView;
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        progressView.setVisibility(GONE);
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);

        /******************************************************************************************/
        int progressViewId = a.getResourceId(R.styleable.StateLayout_progressView, -1);
        if(progressViewId == -1)
        {
            progressView = inflater.inflate(R.layout.view_progress,this,false);
            progressTextView = (TextView) progressView.findViewById(R.id.progressTextView);
        }
        else
        {
            progressView = inflater.inflate(progressViewId,this,false);
        }
        addView(progressView);
        /******************************************************************************************/



        /******************************************************************************************/
        int errorViewId = a.getResourceId(R.styleable.StateLayout_errorView, -1);
        if(errorViewId == -1)
        {
            errorView = inflater.inflate(R.layout.view_error,this,false);
            errorTextView = (TextView) errorView.findViewById(R.id.errorTextView);
            errorButton = (Button) errorView.findViewById(R.id.errorButton);
        }
        else
        {
            errorView = inflater.inflate(errorViewId,this,false);
        }

        addView(errorView);
        /******************************************************************************************/



        /******************************************************************************************/
        int emptyViewId = a.getResourceId(R.styleable.StateLayout_emptyView, -1);
        if(emptyViewId == -1)
        {
            emptyView = inflater.inflate(R.layout.view_empty,this,false);
            emptyTextView = (TextView) emptyView.findViewById(R.id.emptyTextView);
            emptyButton = (Button) emptyView.findViewById(R.id.emptyButton);
        }
        else
        {
            emptyView = inflater.inflate(emptyViewId,this,false);
        }

        addView(emptyView);
        /******************************************************************************************/
        a.recycle();
    }

    private void checkIsContentView(View view)
    {
        if(contentView == null && view != errorView && view != progressView && view != emptyView)
        {
            contentView = view;
        }
    }


    private View currentShowingView;
    public void switchWithAnimation(final View toBeShown)
    {
        final View toBeHided = currentShowingView;
        if(toBeHided == toBeShown)
            return;

        if(toBeHided != null)
        {
            toBeHided.animate()
                    .scaleX(0)
                    .scaleY(0)
                    .alpha(0)
                    .setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toBeHided.setVisibility(GONE);
                }
            });

        }

        if(toBeShown != null)
        {
            currentShowingView = toBeShown;
            toBeShown.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .alpha(1)
                    .setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toBeShown.setVisibility(VISIBLE);
                }
            });
        }
    }



    public void showContentView()
    {
        switchWithAnimation(contentView);
    }
    public void showEmptyView()
    {
        showEmptyView(null);
    }

    public void showEmptyView(String msg)
    {
        onHideContentView();
        emptyTextView.setText(msg);
        switchWithAnimation(emptyView);
    }

    public void showErrorView()
    {
        showErrorView(null);
    }

    public void showErrorView(String msg)
    {
        onHideContentView();
        if (msg != null)
            errorTextView.setText(msg);
        switchWithAnimation(errorView);
    }

    public void showProgressView()
    {
        showProgressView(null);
    }
    public void showProgressView(String msg)
    {
        onHideContentView();
        if (msg != null)
            progressTextView.setText(msg);
        switchWithAnimation(progressView);
    }

    public void setErrorAction(String action, final OnClickListener onErrorButtonClickListener)
    {
        errorButton.setText(action);
        errorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onErrorButtonClickListener != null)
                {
                    onErrorButtonClickListener.onClick(v);
                }
            }
        });
    }


    public void setEmptyAction(String action, final OnClickListener onEmptyButtonClickListener)
    {
        emptyButton.setText(action);
        emptyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onEmptyButtonClickListener != null)
                {
                    onEmptyButtonClickListener.onClick(v);
                }
            }
        });
    }

    protected void onHideContentView()
    {
        //Override me
    }



    /**
     * addView
     */

    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}
