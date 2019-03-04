package org.senani.sachith.story2;

import android.content.ContentValues;
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

import org.senani.sachith.story2.Operation.DBOperationsMy;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.StoryR;
import org.senani.sachith.story2.Other.Values;

public class WriteActivityNp extends AppCompatActivity implements View.OnClickListener{

    private Button btnDelete;
    private Button btnPublish;
    private Button btnSave;
    private EditText body;
    private EditText title;
    private int position;
    private StoryR story;
    private String key;
    public RelativeLayout load;
    public LottieAnimationView loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_np);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnSave = (Button) findViewById(R.id.btnSave);
        body = (EditText) findViewById(R.id.editTextBody);
        title = (EditText) findViewById(R.id.editTextTitle);
        load= (RelativeLayout) findViewById(R.id.load);
        loadingAnim= (LottieAnimationView) findViewById(R.id.loading_anim);

        position=getIntent().getIntExtra("position",0);
        story= Values.MyList.get(position);

        title.setText(story.getT());
        body.setText(story.getB());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPublish) {
            if(Values.mainActivity.isInternetOn()) {

                load.setVisibility(View.VISIBLE);
                loadingAnim.playAnimation();

                final Story storySample = new Story(title.getText().toString(),
                        body.getText().toString(),
                        Values.name
                );

                key = Values.mDatabase.push().getKey().replace("[", "").replace("]", "").replace(" ", "");

                Values.mDatabase.child("story").child(key).setValue(storySample).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                btnPublish.setClickable(false);
                                btnDelete.setClickable(false);
                                story.setT(title.getText().toString());
                                story.setB(body.getText().toString());
                                Values.myStoryListAdapter.notifyDataSetChanged();
                                DBOperationsMy.addNp(storySample, key, story, WriteActivityNp.this);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WriteActivityNp.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                showToast();
            }
        }else if (v.getId() == R.id.btnDelete) {
            if(DBOperationsMy.deleteStoryNp(story.getI())==1){
                Values.MyList.remove(position);
                Values.myStoryListAdapter.notifyDataSetChanged();
                this.finish();
                showToastChecked();
            }
        }else if (v.getId() == R.id.btnSave) {
            ContentValues cv = new ContentValues();
            cv.put("t", title.getText().toString());
            cv.put("b", body.getText().toString());
            Values.mSQLDatabase.update("mystorynp", cv, "i=\"" + story.getI()+"\"", null);
            story.setT(title.getText().toString());
            story.setB(body.getText().toString());
            Values.myStoryListAdapter.notifyDataSetChanged();
            showToastChecked();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }

    @Override
    public void onBackPressed(){
        if(load.getVisibility()==View.INVISIBLE){
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
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
}
