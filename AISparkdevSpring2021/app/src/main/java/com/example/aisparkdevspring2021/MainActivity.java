package com.example.aisparkdevspring2021;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aisparkdevspring2021.Cards.arrayAdapter;
import com.example.aisparkdevspring2021.Cards.cards;
import com.example.aisparkdevspring2021.Matches.MatchesActivity;
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

import static com.example.aisparkdevspring2021.BaseApp.CHANNEL_1_ID;
import static com.example.aisparkdevspring2021.BaseApp.CHANNEL_2_ID;

public class MainActivity extends Activity {

    private cards cards_data[];
    private com.example.aisparkdevspring2021.Cards.arrayAdapter arrayAdapter;
    private int i;
    private boolean backVisible;
    private FirebaseAuth mAuth;
    private String currentUId;

    private DatabaseReference userDb, matchDb;
    private NotificationManagerCompat nManager;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        backVisible = false;
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();
        nManager = NotificationManagerCompat.from(this);

        checkUserSex();

        rowItems = new ArrayList<cards>();
        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);

        matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUId).child("connections").child("matches");


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
                String userId = Obj.getUserId();

                userDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);

                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onRightCardExit(Object dataObject) {
                cards Obj = (cards) dataObject;
                String userId = Obj.getUserId();

                userDb.child(userId).child("connections").child("yep").child(currentUId).setValue(true);
               
                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                //FIXME: set view here!!!!
                FrameLayout fm1 = (FrameLayout) findViewById(R.id.card_front);
                FrameLayout fm2 = (FrameLayout) findViewById(R.id.card_back);

                if(!backVisible)
                {
                    fm1.setVisibility(View.INVISIBLE);
                    fm2.setVisibility(View.VISIBLE);
                }
                else
                {
                    fm2.setVisibility(View.INVISIBLE);
                    fm1.setVisibility(View.VISIBLE);
                }

                backVisible = !backVisible;

            }
        });

    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = userDb.child(currentUId).child("connections").child("yep").child(userId);
        currentUserConnectionsDb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "new Connection", Toast.LENGTH_LONG).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    userDb.child(snapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(key);
                    userDb.child(currentUId).child("connections").child("matches").child(snapshot.getKey()).child("ChatId").setValue(key);
                    matchDb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                sendOnChannel1(findViewById(android.R.id.content).getRootView());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
        DatabaseReference usersDb = userDb.child(user.getUid());
        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                    if (snapshot.exists() && !snapshot.child("connections").child("nope").hasChild(currentUId) && !snapshot.child("connections").child("yep").hasChild(currentUId) && snapshot.child("sex").getValue().toString().equals(potentialMatchSex)) {
                        String profileImageUrl = "default";
                        if (!snapshot.child("profileImageUrl").getValue().equals("default")){
                            profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                        }
                        cards Item = new cards(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileImageUrl, snapshot.child("bio").getValue().toString());
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

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }

    public void sendOnChannel1(View v)
    {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_attach_email_24)
                .setContentTitle("You have matched with someone!")
                .setContentText("Find out who!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .build();

        nManager.notify(1, notification);
    }


}