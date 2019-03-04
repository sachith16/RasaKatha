package org.senani.sachith.story2.Operation;

import android.database.Cursor;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;

/**
 * Created by sachith on 12/21/17.
 */

public class DBOperationsLike {

    public static void addStory(StoryR story, LottieAnimationView btnLike){
        try {
            String insertSQL = "INSERT INTO likedstory \n" +
                    "(i)\n" +
                    "VALUES \n" +
                    "(?);";

            Values.mSQLDatabase.execSQL(insertSQL,new String[]{story.getI()});
            btnLike.setClickable(false);
        }catch (Exception e){

        }
    }

    public static boolean checkIfLiked(StoryR story){
        boolean check=false;

        try {
            Cursor cursorStory = Values.mSQLDatabase.rawQuery("SELECT i FROM likedstory WHERE i = \'" + story.getI() + "\';", null);

            if (cursorStory.moveToFirst()) {
                check = true;
            }
            cursorStory.close();
        }catch (Exception e){

        }

        return check;
    }
}
