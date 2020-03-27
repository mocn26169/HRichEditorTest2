package com.huangdali.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hdl.hricheditorview.R;
import com.huangdali.bean.RichEditorAdapter;
import com.huangdali.utils.ScreenUtil;

/**
 * Instruction:用于测试的popuwindow
 * <p>
 * Author:pei
 * Date: 2017/6/28
 * Description:
 */


public class TestPop extends BasePopupWindow {
    private OnActivityListener onActivityListener;
    private   int pos;
    public TestPop(Context context, OnActivityListener onActivityListener,int pos) {
        super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, R.layout.popwindow_save, context);
        this.onActivityListener = onActivityListener;
        this.pos = pos;
        initView();
        initData();
        setListener();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        setFocusable(true);
        setAnimationStyle(R.style.popuwindow_middle_style);//popuwindow显示隐藏的动画

    }

    @Override
    protected void setListener() {
        mLayoutView.findViewById(R.id.layout_addImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onActivityListener.onAddImage(pos);

//                Log.e("setListener", "layout_addImage");
            }
        });
        mLayoutView.findViewById(R.id.layout_addWord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onActivityListener.onAddWord(pos);
//                Log.e("setListener", "layout_addWord");
            }
        });
    }

    /**
     *
     */
    public interface OnActivityListener {
        void onAddWord(int pos);

        void onAddImage(int pos);

    }
}
