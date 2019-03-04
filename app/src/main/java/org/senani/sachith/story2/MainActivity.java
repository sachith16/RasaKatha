package org.senani.sachith.story2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.senani.sachith.story2.Adapter.LikedStoryListAdapter;
import org.senani.sachith.story2.Adapter.MyStoryListAdapter;
import org.senani.sachith.story2.Adapter.SavedStoryListAdapter;
import org.senani.sachith.story2.Adapter.StoryListAdapter;
import org.senani.sachith.story2.Operation.FBOperationsLiked;
import org.senani.sachith.story2.Operation.FBOperationsMain;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ImageButton button;
    private Button againButton;

    private Intent popIntent;
    private Intent bodyIntent;
    private Intent bodySavedIntent;
    private Intent bodyLikedIntent;
    private Intent bodyMyIntent;
    private Intent bodyMyNpIntent;
    public ListView listView;
    public RelativeLayout load;
    public LottieAnimationView loadingAnim;
    public RelativeLayout error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        load= (RelativeLayout) findViewById(R.id.load);
        loadingAnim= (LottieAnimationView) findViewById(R.id.loading_anim);
        error= (RelativeLayout) findViewById(R.id.error);

        Values.cm =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        Values.mDatabase = FirebaseDatabase.getInstance().getReference();
        Values.mSQLDatabase = openOrCreateDatabase(Values.DATABASE_NAME, MODE_PRIVATE, null);
        Values.MainList= new ArrayList<StoryR>();
        Values.LikedList= new ArrayList<StoryR>();
        Values.MyList= new ArrayList<StoryR>();
        Values.SavedList= new ArrayList<StoryR>();
        Values.storyListAdapter=new StoryListAdapter(getApplicationContext());
        Values.savedStoryListAdapter=new SavedStoryListAdapter(getApplicationContext());
        Values.likedStoryListAdapter=new LikedStoryListAdapter(getApplicationContext());
        Values.myStoryListAdapter=new MyStoryListAdapter(getApplicationContext());

        Values.mainActivity = this;
        button = (ImageButton) findViewById(R.id.btn);
        againButton = (Button) findViewById(R.id.btn_again);
        popIntent=new Intent(getApplicationContext(),PopActivity.class);
        bodyIntent=new Intent(getApplicationContext(),BodyActivity.class);
        bodySavedIntent=new Intent(getApplicationContext(),BodyActivitySaved.class);
        bodyLikedIntent=new Intent(getApplicationContext(),BodyActivityLiked.class);
        bodyMyIntent=new Intent(getApplicationContext(),WriteActivity.class);
        bodyMyNpIntent=new Intent(getApplicationContext(),WriteActivityNp.class);

        Values.sharedPreferences=getSharedPreferences(Values.DATABASE_NAME, Context.MODE_PRIVATE);
        if(Values.sharedPreferences.getBoolean("isFirstTime",true)) {
            createStoryTable();
            SharedPreferences.Editor editor = Values.sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.commit();
        }
        Values.name=Values.sharedPreferences.getString("name","");
        Values.number=Values.sharedPreferences.getString("number","");

        listView.setAdapter(Values.storyListAdapter);
        Values.LikedList.clear();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.getAdapter() instanceof StoryListAdapter) {
                    startActivity(bodyIntent.putExtra("position",position));
                }else if(listView.getAdapter() instanceof SavedStoryListAdapter) {
                    startActivity(bodySavedIntent.putExtra("position",position));
                }else if(listView.getAdapter() instanceof LikedStoryListAdapter) {
                    startActivity(bodyLikedIntent.putExtra("position",position));
                }else {
                    if(Values.MyList.get(position).getI().matches("[0-9]+")){
                        startActivity(bodyMyNpIntent.putExtra("position", position));
                    }else {
                        startActivity(bodyMyIntent.putExtra("position", position));
                    }
                }

                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(popIntent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listView.getAdapter() instanceof StoryListAdapter) {
                    FBOperationsMain.load();
                }else if (listView.getAdapter() instanceof LikedStoryListAdapter) {
                    FBOperationsLiked.load();
                }
            }
        });

        MobileAds.initialize(this, "ca-app-pub-9724878043406666~5067483024");
        FBOperationsMain.load();

    }

    public final boolean isInternetOn() {

        try {
            // Check for network connections
            if (Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

                return true;

            } else if (
                    Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

                return false;
            }
            return true;
        }catch (Exception e){
            return true;
        }
    }

    private void createStoryTable() {

        Values.mSQLDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS mystory (\n" +
                        "    i varchar(20) PRIMARY KEY,\n" +
                        "    t varchar(200) NOT NULL,\n" +
                        "    b varchar(2000) NOT NULL"+
                        ");"
        );

        Values.mSQLDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS savedstory (\n" +
                        "    i varchar(20) PRIMARY KEY,\n" +
                        "    t varchar(200) NOT NULL,\n" +
                        "    b varchar(2000) NOT NULL,\n" +
                        "    w varchar(100) NOT NULL"+
                        ");"
        );

        Values.mSQLDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS likedstory (\n" +
                        "    i varchar(20) PRIMARY KEY"+
                        ");"
        );

        Values.mSQLDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS mystorynp (\n" +
                        "    i INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    t varchar(200) NOT NULL,\n" +
                        "    b varchar(2000) NOT NULL"+
                        ");"
        );
    }

    @Override
    public void onBackPressed() {
        if (!(listView.getAdapter() instanceof StoryListAdapter)) {
            if(Values.MainList.size()<=0){
                Values.mainActivity.listView.setAdapter(Values.storyListAdapter);
                FBOperationsMain.load();
            }else {
                Values.mainActivity.listView.setAdapter(Values.storyListAdapter);
                Values.storyListAdapter.notifyDataSetChanged();
            }
        }else {
            super.onBackPressed();
        }
    }

    public void showToast(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }

    public void showToastChecked(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast_finish, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }
    /*@Override
    public void onDestroy() {
        super.onDestroy();
        //Values.MainList.clear();
        //Values.LikedList.clear();
    }*/

}
