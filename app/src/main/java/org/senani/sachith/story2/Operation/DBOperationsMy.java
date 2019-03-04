package org.senani.sachith.story2.Operation;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.senani.sachith.story2.FinalActivity;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;
import org.senani.sachith.story2.WriteActivityNp;

/**
 * Created by sachith on 12/20/17.
 */

public class DBOperationsMy {

    public static void load(){

        Values.mainActivity.load.setVisibility(View.VISIBLE);
        Values.mainActivity.loadingAnim.playAnimation();

        Cursor cursorStory = Values.mSQLDatabase.rawQuery("SELECT * FROM mystory;", null);

        if (cursorStory!=null && cursorStory.moveToFirst()) {
            do {
                Values.MyList.add(new StoryR(
                        cursorStory.getString(0),
                        cursorStory.getString(1),
                        cursorStory.getString(2)
                ));
            } while (cursorStory.moveToNext());
        }

        cursorStory = Values.mSQLDatabase.rawQuery("SELECT * FROM mystorynp;", null);

        if (cursorStory!=null && cursorStory.moveToFirst()) {
            do {
                Values.MyList.add(new StoryR(
                        String.valueOf(cursorStory.getInt(0)),
                        cursorStory.getString(1),
                        cursorStory.getString(2)
                ));
            } while (cursorStory.moveToNext());
        }

        cursorStory.close();

        Values.mainActivity.loadingAnim.cancelAnimation();
        Values.mainActivity.load.setVisibility(View.INVISIBLE);
    }

    public static void add(Story storySample,String key,FinalActivity wa){
        try {
            String insertSQL = "INSERT INTO mystory \n" +
                    "(i,t, b)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            Values.mSQLDatabase.execSQL(insertSQL,
                    new String[]{key,
                            storySample.getT(),
                            storySample.getB()
                    }
            );

            Values.title="";
            Values.body="";
            OperationsUser.addStory(key,wa);

        }catch (Exception e){
            Toast.makeText(wa, "Error occured while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public static void addNp(Story storySample,FinalActivity wa){
        try {
            String insertSQL = "INSERT INTO mystorynp \n" +
                    "(t, b)\n" +
                    "VALUES \n" +
                    "(?, ?);";

            Values.mSQLDatabase.execSQL(insertSQL,
                    new String[]{
                            storySample.getT(),
                            storySample.getB()
                    }
            );

            Values.title="";
            Values.body="";
            wa.finish();
            Values.mainActivity.showToastChecked();

        }catch (Exception e){
            Toast.makeText(wa, "Error occured while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public static void addNp(Story storySample,String key,StoryR story,WriteActivityNp wa){
        try {
            String insertSQL = "INSERT INTO mystory \n" +
                    "(i,t, b)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            Values.mSQLDatabase.execSQL(insertSQL,
                    new String[]{key,
                            storySample.getT(),
                            storySample.getB()
                    }
            );
            deleteStoryNp(story.getI());
            story.setI(key);
            Values.myStoryListAdapter.notifyDataSetChanged();
            OperationsUser.addStory(key,wa);
        }catch (Exception e){
            Toast.makeText(wa, "Error occured while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public static int deleteStoryNp(String i){
        try {
            return Values.mSQLDatabase.delete("mystorynp", "i = ?", new String[]{i});
        }catch (Exception e){
            return 0;
        }
    }
}
