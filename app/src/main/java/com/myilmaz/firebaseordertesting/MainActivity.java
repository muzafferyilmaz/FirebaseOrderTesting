package com.myilmaz.firebaseordertesting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://ordertesting.firebaseio.com/";
    private static final String TAG = "MainActivity";

    private TextView mTvLastItem;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        mFirebase = new Firebase(FIREBASE_URL);

        mTvLastItem = (TextView) findViewById(R.id.tv_main_last_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getLastItem(View view) {
        new Firebase(FIREBASE_URL).orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String text = dataSnapshot.getValue(String.class);
                Log.i(TAG, "onDataChange single event: " + text);
                mTvLastItem.setText("Last item: " + text);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "onCancelled single event");
            }
        });
    }

    public void registerChildAdded(View view) {
        mAdapter.clearData();
        mFirebase.orderByKey().addChildEventListener(mChildEventListener);
    }

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String text = dataSnapshot.getValue(String.class);
            Log.i(TAG, "onChildAdded: " + text);
            mAdapter.addData(text);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.i(TAG, "onChildChanged");
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.i(TAG, "onChildRemoved");
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.i(TAG, "onChildMoved");
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.i(TAG, "onCancelled");
        }
    };

    public void addFirst(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        mFirebase.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Toast.makeText(MainActivity.this, "added 1,2,3", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetAll(View view) {
        mFirebase.removeValue(new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Toast.makeText(MainActivity.this, "reset completed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void unregister(View view) {
        mFirebase.removeEventListener(mChildEventListener);
        Toast.makeText(MainActivity.this, "Unregistered.", Toast.LENGTH_SHORT).show();
    }
}
