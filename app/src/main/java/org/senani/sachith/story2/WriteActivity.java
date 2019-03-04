package org.senani.sachith.story2;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.senani.sachith.story2.Operation.DBOperationsLike;
import org.senani.sachith.story2.Operation.DBOperationsMy;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;

public class WriteActivity extends AppCompatActivity {

    private Button btnUpdate;
    private EditText body;
    private EditText title;
    private int position;
    private StoryR story;
    public RelativeLayout load;
    public LottieAnimationView loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        body = (EditText) findViewById(R.id.editTextBody);
        title = (EditText) findViewById(R.id.editTextTitle);
        load= (RelativeLayout) findViewById(R.id.load);
        loadingAnim= (LottieAnimationView) findViewById(R.id.loading_anim);

        position=getIntent().getIntExtra("position",0);
        story= Values.MyList.get(position);

        title.setText(story.getT());
        body.setText(story.getB());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Values.mainActivity.isInternetOn()) {

                    load.setVisibility(View.VISIBLE);
                    loadingAnim.playAnimation();

                    try {
                        DatabaseReference postRef = Values.mDatabase.child("story").child(story.getI());
                        postRef.runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {

                                Story p = mutableData.getValue(Story.class);
                                if (p == null) {
                                    return Transaction.success(mutableData);
                                }

                                p.setT(title.getText().toString());
                                p.setB(body.getText().toString());
                                p.setW(Values.name);

                                mutableData.setValue(p);
                                return Transaction.success(mutableData);

                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b,
                                                   DataSnapshot dataSnapshot) {
                                try {
                                    ContentValues cv = new ContentValues();
                                    cv.put("t", title.getText().toString());
                                    cv.put("b", body.getText().toString());
                                    Values.mSQLDatabase.update("mystory", cv, "i=\"" + story.getI() + "\"", null);
                                    story.setT(title.getText().toString());
                                    story.setB(body.getText().toString());
                                    Values.myStoryListAdapter.notifyDataSetChanged();
                                    loadingAnim.cancelAnimation();
                                    load.setVisibility(View.INVISIBLE);
                                    showToastChecked();
                                } catch (Exception e) {
                                    Toast.makeText(WriteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(WriteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showToast();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
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
}
