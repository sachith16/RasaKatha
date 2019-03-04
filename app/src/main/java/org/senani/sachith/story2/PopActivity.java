package org.senani.sachith.story2;

import android.content.Intent;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.senani.sachith.story2.Adapter.LikedStoryListAdapter;
import org.senani.sachith.story2.Adapter.SavedStoryListAdapter;
import org.senani.sachith.story2.Adapter.StoryListAdapter;
import org.senani.sachith.story2.Operation.DBOperationsMy;
import org.senani.sachith.story2.Operation.DBOperationsSaved;
import org.senani.sachith.story2.Operation.FBOperationsLiked;
import org.senani.sachith.story2.Operation.FBOperationsMain;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;

import java.util.Arrays;
import java.util.HashMap;

public class PopActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout writeBtn;
    RelativeLayout likedBtn;
    RelativeLayout savedBtn;
    RelativeLayout profileBtn;
    RelativeLayout myBtn;
    RelativeLayout homeBtn;
    ImageView writead;
    RelativeLayout reloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*1));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.RIGHT;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        writeBtn= (RelativeLayout) findViewById(R.id.write_btn);
        likedBtn= (RelativeLayout) findViewById(R.id.liked_btn);
        savedBtn= (RelativeLayout) findViewById(R.id.saved_btn);
        myBtn= (RelativeLayout) findViewById(R.id.my_btn);
        profileBtn= (RelativeLayout) findViewById(R.id.profile_btn);
        homeBtn = (RelativeLayout) findViewById(R.id.home_btn);
        reloadBtn = (RelativeLayout) findViewById(R.id.reload);
        writead=(ImageView) findViewById(R.id.writead);
        writeBtn.setOnClickListener(this);
        likedBtn.setOnClickListener(this);
        savedBtn.setOnClickListener(this);
        myBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        writead.setOnClickListener(this);
        reloadBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }

    @Override
    public void onPause(){
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.saved_btn){
            if(Values.mainActivity.listView.getVisibility()==View.INVISIBLE) {
                Values.mainActivity.listView.setVisibility(View.VISIBLE);
            }
            if(Values.mainActivity.error.getVisibility()==View.VISIBLE){
                Values.mainActivity.error.setVisibility(View.INVISIBLE);
            }

            if(Values.mainActivity.load.getVisibility()==View.VISIBLE){
                Values.mainActivity.loadingAnim.cancelAnimation();
                Values.mainActivity.load.setVisibility(View.INVISIBLE);
            }

            Values.SavedList.clear();
            DBOperationsSaved.load();
            Values.mainActivity.listView.setAdapter(Values.savedStoryListAdapter);
            Values.savedStoryListAdapter.notifyDataSetChanged();
        }else if(v.getId()==R.id.write_btn){
            if(Values.name.isEmpty() || Values.number.isEmpty()){
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), WriteActivityFirst.class));
            }
        }else if(v.getId()==R.id.liked_btn){
            Values.mainActivity.listView.setAdapter(Values.likedStoryListAdapter);
            if(Values.LikedList.size()<=0){
                FBOperationsLiked.load();
            }else {
                Values.likedStoryListAdapter.notifyDataSetChanged();
            }

        }else if(v.getId()==R.id.my_btn){
            if(Values.mainActivity.listView.getVisibility()==View.INVISIBLE) {
                Values.mainActivity.listView.setVisibility(View.VISIBLE);
            }
            if(Values.mainActivity.error.getVisibility()==View.VISIBLE){
                Values.mainActivity.error.setVisibility(View.INVISIBLE);
            }

            if(Values.mainActivity.load.getVisibility()==View.VISIBLE){
                Values.mainActivity.loadingAnim.cancelAnimation();
                Values.mainActivity.load.setVisibility(View.INVISIBLE);
            }

            Values.MyList.clear();
            DBOperationsMy.load();
            Values.mainActivity.listView.setAdapter(Values.myStoryListAdapter);
            Values.myStoryListAdapter.notifyDataSetChanged();
        }else if(v.getId()==R.id.profile_btn){

            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }else if(v.getId()==R.id.home_btn){
            Values.mainActivity.listView.setAdapter(Values.storyListAdapter);
            if(Values.MainList.size()<=0){
                FBOperationsMain.load();
            }else {
                Values.storyListAdapter.notifyDataSetChanged();
            }
        }else if(v.getId()==R.id.writead){
            if(Values.name.isEmpty() || Values.number.isEmpty()){
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), WriteActivityFirst.class));
            }
        }else if(v.getId()==R.id.reload){
            if (Values.mainActivity.listView.getAdapter() instanceof StoryListAdapter) {
                Values.MainList.clear();
                FBOperationsMain.load();
            }else if (Values.mainActivity.listView.getAdapter() instanceof LikedStoryListAdapter) {
                Values.LikedList.clear();
                FBOperationsLiked.load();
            }
        }

        onBackPressed();
    }
}
