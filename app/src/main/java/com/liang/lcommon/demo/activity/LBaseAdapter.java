package com.liang.lcommon.demo.activity;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liang.lcommon.R;

import java.util.List;

/**
 * @author : Amarao
 * CreateAt : 8:10 2019/1/19
 * Describe : 适配器
 */
public class LBaseAdapter extends BaseQuickAdapter<LBaseItemBean, BaseViewHolder> {


    public LBaseAdapter(@Nullable List<LBaseItemBean> data) {
        super(R.layout.activity_intorduce_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LBaseItemBean item) {
        helper.setText(R.id.item_intro, item.getIntro());
        helper.setImageResource(R.id.item_image, item.getSrc());

        // imageView接收点击事件，并转移给itemView
        ImageView imageView = helper.getView(R.id.item_image);
        imageView.setOnClickListener(v -> {
            helper.itemView.performClick(); // 提交图片的点击效果给itemView
        });
    }
}
