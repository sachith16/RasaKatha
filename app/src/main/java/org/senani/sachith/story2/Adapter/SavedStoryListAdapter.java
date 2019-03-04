package org.senani.sachith.story2.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.R;
import org.senani.sachith.story2.Other.Values;

public class SavedStoryListAdapter extends BaseAdapter {

    private Context mContext;

    public SavedStoryListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Values.SavedList.size();
    }

    @Override
    public Object getItem(int position) {
        return Values.SavedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
            v = View.inflate(mContext, R.layout.item_list2, null);

            TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
            TextView tvAuthor = (TextView) v.findViewById(R.id.tv_author);
            StoryR story=Values.SavedList.get(position);

            tvTitle.setText(story.getT());
            tvAuthor.setText(story.getW());

            v.setTag(Values.SavedList.get(position).getI());

        return v;
    }
}
