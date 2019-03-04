package org.senani.sachith.story2.Adapter;

import android.content.Context;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.senani.sachith.story2.Operation.FBOperationsLiked;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.R;
import org.senani.sachith.story2.Other.Values;

public class LikedStoryListAdapter extends BaseAdapter {

    private Context mContext;

    public LikedStoryListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Values.LikedList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return Values.LikedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if(position==Values.LikedList.size() && Values.LikedList.size()>0){
            v = View.inflate(mContext, R.layout.item_btn, null);
            final Button btnMore=(Button)v.findViewById(R.id.btn_more);
            final LottieAnimationView anim=(LottieAnimationView) v.findViewById(R.id.loading_anim);

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Values.mainActivity.isInternetOn()){
                        btnMore.setVisibility(View.INVISIBLE);
                        anim.setVisibility(View.VISIBLE);
                        anim.playAnimation();
                        FBOperationsLiked.loadMore();
                    }else {
                        Values.mainActivity.showToast();
                    }
                }
            });


        }else if (Values.LikedList.size()>0){
            v = View.inflate(mContext, R.layout.item_list, null);

            TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
            TextView tvAuthor = (TextView) v.findViewById(R.id.tv_author);
            TextView tvLikes = (TextView) v.findViewById(R.id.tv_likes);
            StoryR story=Values.LikedList.get(position);

            tvTitle.setText(story.getT());
            tvAuthor.setText(story.getW());
            tvLikes.setText(story.getL()+"");

            v.setTag(Values.LikedList.get(position).getI());
        }else {
            v = View.inflate(mContext, R.layout.item_load, null);
        }

        return v;
    }
}
