package org.senani.sachith.story2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.senani.sachith.story2.Operation.DBOperationsLike;
import org.senani.sachith.story2.Operation.DBOperationsSaved;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;

public class BodyActivitySaved extends AppCompatActivity {
    TextView tvBody;
    TextView tvTitle;
    TextView tvWriter;
    Button btnDelete;

    int position;

    StoryR story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_saved);

        tvBody=(TextView) findViewById(R.id.tv_body);
        tvWriter=(TextView) findViewById(R.id.tv_writer);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        btnDelete= (Button) findViewById(R.id.btn_save);

        position=getIntent().getIntExtra("position",0);
        story= Values.SavedList.get(position);

        tvBody.setText(story.getB());
        tvTitle.setText(story.getT());
        tvWriter.setText("-"+story.getW()+"-");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBOperationsSaved.deleteStory(story)==1){
                    Values.SavedList.remove(position);
                    Values.savedStoryListAdapter.notifyDataSetChanged();
                    onBackPressed();
                    Values.mainActivity.showToastChecked();
                }
            }
        });

    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }


}