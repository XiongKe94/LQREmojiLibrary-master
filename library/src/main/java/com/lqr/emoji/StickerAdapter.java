package com.lqr.emoji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * 每屏显示的贴图
 */
public class StickerAdapter extends BaseAdapter {

    private Context context;
    private StickerCategory category;
    private int startIndex;

    public StickerAdapter(Context mContext, StickerCategory category, int startIndex) {
        this.context = mContext;
        this.category = category;
        this.startIndex = startIndex;
    }

    public int getCount() {//获取每一页的数量
        int count = category.getStickers().size() - startIndex;
        count = Math.min(count, EmoticonView.STICKER_PER_PAGE);
        return count;
    }

    @Override
    public Object getItem(int position) {
        return category.getStickers().get(startIndex + position);
    }

    @Override
    public long getItemId(int position) {
        return startIndex + position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        StickerViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.nim_sticker_picker_view, null);
            viewHolder = new StickerViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.sticker_thumb_image);
            viewHolder.descLabel = (TextView) convertView.findViewById(R.id.sticker_desc_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StickerViewHolder) convertView.getTag();
        }

        int index = startIndex + position;
        if (index >= category.getStickers().size()) {
            return convertView;
        }

        StickerItem sticker = category.getStickers().get(index);
        if (sticker == null) {
            return convertView;
        }

        Glide.with(convertView.getContext()).load(StickerManager.getInstance().
                getStickerBitmapUri(sticker.getCategory(),sticker.getName())).
                error(R.drawable.nim_default_img_failed)
                .centerCrop().override(LQRUIKit.dip2px(64),LQRUIKit.dip2px(64)).into(viewHolder.imageView);


        viewHolder.descLabel.setVisibility(View.GONE);

        return convertView;
    }

    class StickerViewHolder {
        public ImageView imageView;
        public TextView descLabel;
    }
}