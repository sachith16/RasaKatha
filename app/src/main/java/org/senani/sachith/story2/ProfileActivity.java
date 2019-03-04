package org.senani.sachith.story2;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.senani.sachith.story2.Operation.DBOperationsLike;
import org.senani.sachith.story2.Operation.DBOperationsMy;
import org.senani.sachith.story2.Operation.OperationsUser;
import org.senani.sachith.story2.Other.Story;
import org.senani.sachith.story2.Other.User;
import org.senani.sachith.story2.Other.Values;

public class ProfileActivity extends AppCompatActivity {

    EditText name;
    EditText number;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        save = (Button) findViewById(R.id.save);

        name.setText(Values.sharedPreferences.getString("name",""));
        number.setText(Values.sharedPreferences.getString("number",""));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Values.mainActivity.isInternetOn()) {
                    if(!name.getText().toString().isEmpty() && !number.getText().toString().isEmpty()) {
                        Values.mDatabase.child("user").child(number.getText().toString()).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            OperationsUser.update(name.getText().toString(), number.getText().toString(), ProfileActivity.this);
                                        } else {
                                            User user = new User(name.getText().toString());
                                            OperationsUser.add(user, number.getText().toString(), ProfileActivity.this);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }else {
                        showToastEmpty();
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
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void showToast(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }

    public void showToastEmpty(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast_empty_profile, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
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
