package com.example.aisparkdevspring2021;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity {

    private cards cards_data[];
    private arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;
    private String currentUId;

    private DatabaseReference userDb;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        checkUserSex();

        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                cards Obj = (cards) dataObject;
                String userId = Obj.getUserID();

                userDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);

                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onRightCardExit(Object dataObject) {
                cards Obj = (cards) dataObject;
                String userId = Obj.getUserID();

                userDb.child(userId).child("connections").child("yep").child(currentUId).setValue(true);
               
                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                /*
                // Ask for more data here
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
                 */
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = userDb.child(userSex).child(currentUId).child("connections").child("yep").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "new Connection", Toast.LENGTH_LONG).show();
                    userDb.child(potentialMatchSex).child(snapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    userDb.child(userSex).child(currentUId).child("connections").child("matches").child(snapshot.getKey()).setValue(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   

    private String userSex;
    private String potentialMatchSex;

    public void checkUserSex(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference udb = userDb.child(user.getUid());
        udb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        if (snapshot.child("sex").getValue() != null) {
                            userSex = snapshot.child("sex").getValue().toString();
                            switch (userSex)
                            {
                                case "Male":
                                    potentialMatchSex = "Female";
                                    break;
                                case "Female":
                                    potentialMatchSex = "Male";
                                    break;
                            }
                            getOppositeSexUsers();

                        }
                    }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    public void getOppositeSexUsers(){
        userDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists()) {
                   /* String picture = "";
                    if (snapshot.child("profileImageUrl").getValue() == null) {
                        picture = "../../../res/mipmap/ic_launcher/ic_launcher.png";
                    } else picture = snapshot.child("profileImageUrl").getValue().toString();
                    */
                    if (snapshot.exists() && !snapshot.child("connections").child("nope").hasChild(currentUId) && !snapshot.child("connections").child("yep").hasChild(currentUId) && snapshot.child("sex").getValue().toString().equals(potentialMatchSex)) {
                        String profileImageUrl = "default";

                        if (!snapshot.child("profileImageUrl").getValue().equals("default")){
                            profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                        }
                        cards Item = new cards(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileImageUrl);
                        rowItems.add(Item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void LogoutUser(View view){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    //Go to Setting
    public void goToSetting(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        return;
    }
}