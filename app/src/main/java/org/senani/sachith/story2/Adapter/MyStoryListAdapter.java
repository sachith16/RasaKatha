package org.senani.sachith.story2.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.R;
import org.senani.sachith.story2.Other.Values;

public class MyStoryListAdapter extends BaseAdapter {

    private Context mContext;

    public MyStoryListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Values.MyList.size();
    }

    @Override
    public Object getItem(int position) {
        return Values.MyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        StoryR story=Values.MyList.get(position);

        if(story.getI().matches("[0-9]+")){
            v = View.inflate(mContext, R.layout.item_list3, null);

            TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);

            tvTitle.setText(story.getT());

            v.setTag(Values.MyList.get(position).getI());
        }else {
            v = View.inflate(mContext, R.layout.item_list2, null);

            TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
            TextView tvAuthor = (TextView) v.findViewById(R.id.tv_author);

            tvTitle.setText(story.getT());
            tvAuthor.setText("Published");

            v.setTag(Values.MyList.get(position).getI());
        }



        return v;
    }
}
