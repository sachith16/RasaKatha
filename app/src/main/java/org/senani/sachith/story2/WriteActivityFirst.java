package org.senani.sachith.story2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.senani.sachith.story2.Other.Values;

public class WriteActivityFirst extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_first);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTitle.setText(Values.title);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnNext){
            Values.title=editTextTitle.getText().toString();

            if(Values.title.isEmpty()){
                showToast();
            }else {
                startActivity(new Intent(getApplicationContext(), WriteActivitySecond.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed(){

        if(!Values.title.isEmpty() || !Values.body.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to leave without saving?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    WriteActivityFirst.super.onBackPressed();
                    Values.title="";
                    Values.body="";
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }else{
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }

    }

    public void showToast(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast_empty_title, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }
}
