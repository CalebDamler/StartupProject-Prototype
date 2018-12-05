package com.calebco.caleb.prototype1;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //firebase auth

    private android.support.v7.widget.Toolbar toolbar;


    private FirebaseAuth mAuth;
    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private CameraFragment cameraFragment;
    private AccountFragment accountFragment;
    private UploadFragment settingsFragment;
    private InboxFragment inboxFragment;
    private VideosFragment videosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainNav = findViewById(R.id.main_nav);
        mainFrame = findViewById(R.id.main_frame);

        //toolbar and setup
        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Virtual Time Capsule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        mAuth = FirebaseAuth.getInstance();

        cameraFragment = new CameraFragment();
        accountFragment = new AccountFragment();
        settingsFragment = new UploadFragment();
        inboxFragment = new InboxFragment();
        videosFragment = new VideosFragment();

        mainNav.setSelectedItemId(R.id.cameraTab);
        setFragment(cameraFragment);

        //fragment switcher
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //camera
                    //account
                    //settings
                    //inbox
                    case R.id.accountTab:
                        //mainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(accountFragment);
                        return true;
                    case R.id.cameraTab:
                        //mainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(cameraFragment);
                        return true;
                    case R.id.UploadTab:
                        //mainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(settingsFragment);
                        return true;
                    case R.id.inboxTab:
                        //mainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(inboxFragment);
                        return true;
                    case R.id.galleryTabTab:
                        //mainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(videosFragment);
                        return true;
                        default:
                            return false;

                }
            }
        });

    }
    //set the new fragment
    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if the user is not logged in send them to the login page
        if(currentUser == null){
                send();
        }

    }

    private void send(){
        Intent toLogin = new Intent(MainActivity.this, CheckAccountActivity.class);
        startActivity(toLogin);
        finish();
    }

    /*************************************************************
     *onCreateOptionsMenu()
     *
     * populates the menu with the buttons we put in the layout
     *
     *inflate the menu with the buttons we chose
     ************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //connect the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    /*************************************************************
     *Send()
     *
     * sends the user back to the login screen
     *
     * reducing redundant code
     ************************************************************/
    private void Send() {
        //bring to login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class );
        startActivity(intent);
        finish(); // do not return to this page if the back button is clicked
    }

    /*************************************************************
     * onOptionsItemSelected()
     *
     * menu for the top right drop down menu
     *
     * handles which button is pressed from the button in the top right
     * corner
     *
     * settings
     * all useres
     * logout
     * send message
     *
     ************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        //if btn is log out
        if(item.getItemId() == R.id.mainLogOut) {
            //sign the user out
            FirebaseAuth.getInstance().signOut();
            //back to login
            Send();
        }

        //if btn is settings
        if(item.getItemId() == R.id.menu_manage){
            //send them to the settings activity
            Intent intent = new Intent(MainActivity.this, ManageUploads.class);

            startActivity(intent);
        }
/*
        //if button is all users
        if (item.getItemId() == R.id.mainAll){
            //send them to the all users page
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        }

        //if btn is send message
        if (item.getItemId() == R.id.SendMessage){
            //send them to a chat this chat is hardcoded in only allows 2 users that cant be changed
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        }
*/

        return true;
    }
}
