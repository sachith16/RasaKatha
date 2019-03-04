package org.senani.sachith.story2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.senani.sachith.story2.Other.Values;

public class WriteActivitySecond extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextStory;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_second);

        editTextStory = (EditText) findViewById(R.id.editTextStory);
        editTextStory.setText(Values.body);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            Values.body = editTextStory.getText().toString();

            if(Values.body.isEmpty()){
                showToast();
            }else {
                startActivity(new Intent(getApplicationContext(), FinalActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }else if(v.getId()==R.id.btnBack){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Values.body=editTextStory.getText().toString();
        startActivity(new Intent(getApplicationContext(),WriteActivityFirst.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }

    public void showToast(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast_empty_body, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }
}
