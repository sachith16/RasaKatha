package org.senani.sachith.story2.Operation;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;
import org.senani.sachith.story2.WriteActivity;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sachith on 12/20/17.
 */

public class FBOperationsLiked {
    public static Long lastItem;

    public static void load(){

        if(Values.mainActivity.isInternetOn()) {
            //start
            Values.mainActivity.load.setVisibility(View.VISIBLE);
            Values.mainActivity.loadingAnim.playAnimation();
            if(Values.mainActivity.error.getVisibility()==View.VISIBLE){
                Values.mainActivity.error.setVisibility(View.INVISIBLE);
            }

            Values.mDatabase.child("story").orderByChild("l").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.exists()) {
                            Values.LikedList.clear();
                            HashMap<String, HashMap<String, Object>> values = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                            String[] keyset = values.keySet().toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
                            Arrays.sort(keyset);

                            Long last = (Long) values.get(keyset[0]).get("l");

                            for (String key : keyset) {
                                HashMap<String, Object> story = values.get(key);
                                if (story != null) {
                                    Long l = (Long) story.get("l");

                                    Values.LikedList.add(
                                            new StoryR(key,
                                                    String.valueOf(story.get("t")),
                                                    String.valueOf(story.get("b")),
                                                    String.valueOf(story.get("w")),
                                                    l)
                                    );


                                    if (l < last) {
                                        last = (Long) story.get("l");
                                    }
                                }
                            }

                            Values.mainActivity.listView.setAdapter(Values.likedStoryListAdapter);
                            Values.likedStoryListAdapter.notifyDataSetChanged();
                            if (Values.mainActivity.listView.getVisibility() == View.INVISIBLE) {
                                Values.mainActivity.listView.setVisibility(View.VISIBLE);
                            }
                            Values.mainActivity.loadingAnim.cancelAnimation();
                            Values.mainActivity.load.setVisibility(View.INVISIBLE);
                            lastItem = last - 1;
                            Values.mDatabase.removeEventListener(this);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Values.mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            Values.mainActivity.showToast();

            if(Values.mainActivity.error.getVisibility()==View.INVISIBLE) {
                Values.mainActivity.error.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void loadMore(){

        try {
            Values.mDatabase.child("story").orderByChild("l").endAt(lastItem,"l").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.exists()) {
                            HashMap<String, HashMap<String, Object>> values = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                            String[] keyset=values.keySet().toString().replace("[","").replace("]","").replace(" ","").split(",");
                            Arrays.sort(keyset);

                            Long last= (Long) values.get(keyset[0]).get("l");

                            for (String key : keyset) {
                                HashMap<String, Object> story=values.get(key);
                                if (story != null) {

                                    Long l=(Long) story.get("l");

                                    Values.LikedList.add(
                                            new StoryR(key,
                                                    String.valueOf(story.get("t")),
                                                    String.valueOf(story.get("b")),
                                                    String.valueOf(story.get("w")),
                                                    l)
                                    );

                                    if(l<last){
                                        last= (Long) story.get("l");
                                    }
                                }
                            }

                            lastItem=last-1;
                            Values.likedStoryListAdapter.notifyDataSetChanged();
                            Values.mDatabase.removeEventListener(this);
                        }else {
                            Values.likedStoryListAdapter.notifyDataSetChanged();
                            Values.mDatabase.removeEventListener(this);
                        }
                    }catch (Exception e){
                        Toast.makeText(Values.mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(Values.mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
 