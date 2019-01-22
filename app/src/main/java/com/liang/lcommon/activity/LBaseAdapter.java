package com.liang.lcommon.activity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liang.lcommon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Amarao
 * CreateAt : 8:10 2019/1/19
 * Describe :
 */
public class LBaseAdapter extends BaseQuickAdapter<LBaseItemBean, BaseViewHolder> {

    public LBaseAdapter(int layoutResId, @Nullable List<LBaseItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LBaseItemBean item) {
        helper.setText(R.id.item_intro, item.getIntro());
        helper.setImageResource(R.id.item_image, item.getSrc());
    }
}
