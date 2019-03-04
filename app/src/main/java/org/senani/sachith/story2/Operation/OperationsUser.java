package org.senani.sachith.story2.Operation;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.senani.sachith.story2.FinalActivity;
import org.senani.sachith.story2.Other.User;
import org.senani.sachith.story2.Other.Values;
import org.senani.sachith.story2.ProfileActivity;
import org.senani.sachith.story2.WriteActivity;
import org.senani.sachith.story2.WriteActivityNp;

import java.util.ArrayList;

/**
 * Created by sachith on 12/31/17.
 */

public class OperationsUser {

    public static void add(final User user, final String number ,final ProfileActivity pa){

        Values.mDatabase.child("user").child(number).setValue(user).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor = Values.sharedPreferences.edit();
                        editor.putString("name", user.getName());
                        editor.putString("number", number);
                        editor.commit();
                        Values.name=user.getName();
                        Values.number=number;
                        pa.finish();
                        Values.mainActivity.showToastChecked();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void update(final String name, final String number, final ProfileActivity pa){
        DatabaseReference postRef = Values.mDatabase.child("user").child(number);
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User p = mutableData.getValue(User.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                p.setName(name);

                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = Values.sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("number", number);
                editor.commit();
                Values.name=name;
                Values.number=number;
                pa.showToastChecked();
            }
        });
    }

    public static void addStory(final String key, final WriteActivityNp np){

        DatabaseReference postRef = Values.mDatabase.child("user").child(Values.number);
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User p = mutableData.getValue(User.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                ArrayList<String> story=p.getStory();
                story.add(key);
                p.setStory(story);

                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                np.finish();
                Values.mainActivity.showToastChecked();
            }
        });

    }

    public static void addStory(final String key,final FinalActivity fa){

        DatabaseReference postRef = Values.mDatabase.child("user").child(Values.number);
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User p = mutableData.getValue(User.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                ArrayList<String> story=p.getStory();
                story.add(key);
                p.setStory(story);

                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                fa.finish();
                Values.mainActivity.showToastChecked();
            }
        });

    }
}
