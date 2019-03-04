package org.senani.sachith.story2.Other;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;

import com.google.firebase.database.DatabaseReference;

import org.senani.sachith.story2.Adapter.LikedStoryListAdapter;
import org.senani.sachith.story2.Adapter.MyStoryListAdapter;
import org.senani.sachith.story2.Adapter.SavedStoryListAdapter;
import org.senani.sachith.story2.Adapter.StoryListAdapter;
import org.senani.sachith.story2.MainActivity;

import java.util.ArrayList;

/**
 * Created by sachith on 9/9/17.
 */

public class Values {

    public static DatabaseReference mDatabase;

    public static ArrayList<StoryR> MainList ;
    public static ArrayList<StoryR> SavedList ;
    public static ArrayList<StoryR> LikedList ;
    public static ArrayList<StoryR> MyList ;

    public static SharedPreferences sharedPreferences;
    public static ConnectivityManager cm;

    public static final String DATABASE_NAME="Rasakatha";
    public static SQLiteDatabase mSQLDatabase;

    public static MainActivity mainActivity;

    public static StoryListAdapter storyListAdapter;
    public static SavedStoryListAdapter savedStoryListAdapter;
    public static LikedStoryListAdapter likedStoryListAdapter;
    public static MyStoryListAdapter myStoryListAdapter;

    public static String title="";
    public static String body="";
    public static String name;
    public static String number;

}
