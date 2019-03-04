package org.senani.sachith.story2.Operation;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.senani.sachith.story2.BodyActivity;
import org.senani.sachith.story2.BodyActivityLiked;
import org.senani.sachith.story2.BodyActivitySaved;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;
import org.senani.sachith.story2.R;

/**
 * Created by sachith on 12/20/17.
 */

public class DBOperationsSaved {

    public static void addStory(StoryR story, Button btnSave , BodyActivity ba) {
        try {
            String insertSQL = "INSERT INTO savedstory \n" +
                    "(i,t, b,w)\n" +
                    "VALUES \n" +
                    "(?, ?, ?,?);";

            Values.mSQLDatabase.execSQL(insertSQL,
                    new String[]{story.getI(),
                            story.getT(),
                            story.getB(),
                            story.getW()
                    }
            );
            btnSave.setClickable(false);
            btnSave.setBackgroundResource(R.drawable.rounded_button2);
            ba.showToastChecked();
        }catch (Exception e){
            Toast.makeText(ba, "Error occured while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public static void addStory(StoryR story, Button btnSave , BodyActivityLiked ba) {
        try {
            String insertSQL = "INSERT INTO savedstory \n" +
                    "(i,t, b,w)\n" +
                    "VALUES \n" +
                    "(?, ?, ?,?);";

            Values.mSQLDatabase.execSQL(insertSQL,
                    new String[]{story.getI(),
                            story.getT(),
                            story.getB(),
                            story.getW()
                    }
            );
            btnSave.setClickable(false);
            btnSave.setBackgroundResource(R.drawable.rounded_button2);
            ba.showToastChecked();
        }catch (Exception e){
            Toast.makeText(ba, "Error occured while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean checkSavedStory(StoryR story){
        boolean check=false;

        try {
            Cursor cursorStory = Values.mSQLDatabase.rawQuery("SELECT i FROM savedstory WHERE i = \'" + story.getI() + "\';", null);

            if (cursorStory.moveToFirst()) {
                check = true;
            }
            cursorStory.close();
        }catch (Exception e){

        }

        return check;
    }

    public static void load(){

        Values.mainActivity.load.setVisibility(View.VISIBLE);
        Values.mainActivity.loadingAnim.playAnimation();

        Cursor cursorStory = Values.mSQLDatabase.rawQuery("SELECT * FROM savedstory;", null);

        if (cursorStory!=null && cursorStory.moveToFirst()) {
            do {
                Values.SavedList.add(new StoryR(
                        cursorStory.getString(0),
                        cursorStory.getString(1),
                        cursorStory.getString(2),
                        cursorStory.getString(3)
                ));
            } while (cursorStory.moveToNext());
        }

        cursorStory.close();

        Values.mainActivity.loadingAnim.cancelAnimation();
        Values.mainActivity.load.setVisibility(View.INVISIBLE);
    }

    public static int deleteStory(StoryR story){
        try {
            return Values.mSQLDatabase.delete("savedstory", "i = ?", new String[]{story.getI()});
        }catch (Exception e){
            return 0;
        }
    }
}
