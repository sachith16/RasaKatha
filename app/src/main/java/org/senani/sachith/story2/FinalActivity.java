package org.senani.sachith.story2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.senani.sachith.story2.Operation.DBOperationsMy;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.Values;

public class FinalActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewTitle;
    private TextView textViewBody;
    private TextView textViewWriter;

    public String key;

    private Button btnPublish;
    private Button btnSave;
    public RelativeLayout load;
    public LottieAnimationView loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        textViewTitle = (TextView) findViewById(R.id.tv_title);
        textViewBody = (TextView) findViewById(R.id.tv_body);
        textViewWriter=(TextView) findViewById(R.id.tv_writer);
        btnPublish= (Button) findViewById(R.id.btnPublish);
        btnSave= (Button) findViewById(R.id.btnSave);
        load= (RelativeLayout) findViewById(R.id.load);
        loadingAnim= (LottieAnimationView) findViewById(R.id.loading_anim);

        textViewTitle.setText(Values.title);
        textViewBody.setText(Values.body);
        textViewWriter.setText("-"+Values.name+"-");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnPublish){
            if(Values.mainActivity.isInternetOn()) {

                load.setVisibility(View.VISIBLE);
                loadingAnim.playAnimation();

                final Story storySample = new Story(Values.title,
                        Values.body,
                        Values.name
                );

                key = Values.mDatabase.push().getKey().replace("[", "").replace("]", "").replace(" ", "");

                Values.mDatabase.child("story").child(key).setValue(storySample).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                btnPublish.setClickable(false);
                                btnSave.setClickable(false);
                                DBOperationsMy.add(storySample, key, FinalActivity.this);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FinalActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                showToast();
            }
        }else if(v.getId()==R.id.btnSave){
            final Story storySample = new Story(Values.title,
                    Values.body,
                    Values.name
            );
            DBOperationsMy.addNp(storySample,FinalActivity.this);
        }else if(v.getId()==R.id.btnBack){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed(){
        if(load.getVisibility()==View.INVISIBLE) {
            super.onBackPressed();
            startActivity(new Intent(getApplicationContext(), WriteActivitySecond.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
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
}


