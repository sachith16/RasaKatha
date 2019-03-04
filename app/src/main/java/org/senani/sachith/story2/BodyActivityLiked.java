package org.senani.sachith.story2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
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

public class BodyActivityLiked extends AppCompatActivity {
    TextView tvBody;
    TextView tvTitle;
    TextView tvWriter;
    TextView likes;
    Button btnSave;
    Button btnWp;
    LottieAnimationView btnLike;
    int position;

    StoryR story;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_liked);

        tvBody=(TextView) findViewById(R.id.tv_body);
        tvWriter=(TextView) findViewById(R.id.tv_writer);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        btnSave= (Button) findViewById(R.id.btn_save);
        btnWp= (Button) findViewById(R.id.btn_wp);
        btnLike= (LottieAnimationView) findViewById(R.id.btn_like);
        likes=(TextView) findViewById(R.id.like);

        position=getIntent().getIntExtra("position",0);
        story= Values.LikedList.get(position);

        tvBody.setText(story.getB());
        likes.setText(story.getL()+"");
        tvTitle.setText(story.getT());
        tvWriter.setText("-"+story.getW()+"-");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOperationsSaved.addStory(story,btnSave,BodyActivityLiked.this);
            }
        });

        btnWp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, story.getT()+"\n\n"+story.getB()+"\n\n"+"රසකතා ලියන්න");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(BodyActivityLiked.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Values.mainActivity.isInternetOn()) {
                    btnLike.playAnimation();
                    try {
                        DatabaseReference postRef = Values.mDatabase.child("story").child(story.getI());
                        postRef.runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {

                                Story p = mutableData.getValue(Story.class);
                                if (p == null) {
                                    return Transaction.success(mutableData);
                                }

                                p.setL(p.getL() + 1);

                                mutableData.setValue(p);
                                return Transaction.success(mutableData);

                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b,
                                                   DataSnapshot dataSnapshot) {
                                DBOperationsLike.addStory(story, btnLike);
                                story.setL(story.getL() + 1);
                                Values.likedStoryListAdapter.notifyDataSetChanged();
                                likes.setText((Integer.valueOf(likes.getText().toString()) + 1) + "");
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(BodyActivityLiked.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showToast();
                }

            }
        });


        if (DBOperationsSaved.checkSavedStory(story)) {
            btnSave.setClickable(false);
            btnSave.setBackgroundResource(R.drawable.rounded_button2);
        }

        if (DBOperationsLike.checkIfLiked(story)) {
            btnLike.setClickable(false);
            btnLike.setProgress(1f);
        }

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