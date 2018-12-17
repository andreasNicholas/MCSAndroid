package project.aigo.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.R;

public class SplashScreenActivity extends GlobalActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                //seed firebase
                if (!dataSnapshot.exists() ) {
                    Map<String, String> adminDummy = new HashMap<>();
                    adminDummy.put("address" , "aaa");
                    adminDummy.put("birth_date" , "2018-01-01");
                    adminDummy.put("email" , "admin@admin.com");
                    adminDummy.put("gender" , "Male");
                    adminDummy.put("name" , "admin");
                    adminDummy.put("password" , "admin");
                    adminDummy.put("password_confirmation" , "admin");
                    adminDummy.put("phone" , "0000");
                    adminDummy.put("photo", DEFAULT_IMAGE);

                    Map<String, String> athleteDummy = new HashMap<>();
                    athleteDummy.put("address" , "aaa");
                    athleteDummy.put("birth_date" , "2018-01-01");
                    athleteDummy.put("email" , "athlete@athlete.com");
                    athleteDummy.put("gender" , "Male");
                    athleteDummy.put("name" , "athlete");
                    athleteDummy.put("password" , "athlete");
                    athleteDummy.put("password_confirmation" , "athlete");
                    athleteDummy.put("phone" , "0000");
                    athleteDummy.put("photo", DEFAULT_IMAGE);

                    Map<String, String> athlete2Dummy = new HashMap<>();
                    athlete2Dummy.put("address" , "aaa");
                    athlete2Dummy.put("birth_date" , "2018-01-01");
                    athlete2Dummy.put("email" , "athlete2@athlete.com");
                    athlete2Dummy.put("gender" , "Male");
                    athlete2Dummy.put("name" , "athlete2");
                    athlete2Dummy.put("password" , "athlete2");
                    athlete2Dummy.put("password_confirmation" , "athlete2");
                    athlete2Dummy.put("phone" , "0000");
                    athlete2Dummy.put("photo", DEFAULT_IMAGE);

                    myRef.child("users").child("1").setValue(adminDummy);
                    myRef.child("users").child("2").setValue(athleteDummy);
                    myRef.child("users").child("3").setValue(athlete2Dummy);
                    myRef.push();
                }
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }
        });

        final Context context = this;

        SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);
        final String name = sp.getString("name" , null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {

                if (name == null) {
                    startActivity(new Intent(context , LoginActivity.class));

                } else {
                    toastShort(context , "Welcome, " + name);
                    startActivity(new Intent(context , MainActivity.class));
                }
                finish();
            }
        } , 2000);
    }
}
