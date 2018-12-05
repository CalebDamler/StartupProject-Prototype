package com.calebco.caleb.prototype1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TextInputLayout recipientName;
    private TextInputLayout receiveDate;
    private TextInputLayout videoTitle;
    //private TextInputLayout titleTextInput;
    private TextInputLayout phoneTextInput;
    private TextInputLayout emailTextInput;
    private TextInputLayout bPhoneTextInput;
    private TextInputLayout bEmialTextInput;
    private TextView tempUriTextView;
    private Button uploadButton;
    private DatabaseReference databaseReference;
    private String currentUserId;
    private FirebaseAuth mAuth;
    private Uri uri;
    private EditText editName, editDate, editTitle, editPhone, editEmail, editBemail, editBphone;
    private StorageReference mStorageRef;
    private int errorCode = 0;

    //private ArrayList<Uri> videoUriList = new ArrayList<>();
    //private ArrayList videoBitList = new ArrayList<>();

    //data lists
    private ArrayList pictureBitList = new ArrayList<>();
    private VideoListClass videoListClass = new VideoListClass();
    private List<VideoListClass> classList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        toolbar = findViewById(R.id.upToolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Recipient Details");


        tempUriTextView = findViewById(R.id.uriTextView);
        uploadButton = findViewById(R.id.createBtn);
        recipientName = findViewById(R.id.RecipientInput);
        receiveDate = findViewById(R.id.DateSelectInput);
        videoTitle = findViewById(R.id.VideoTitle);
        //titleTextInput = findViewById(R.id.VideoTitle);
        phoneTextInput = findViewById(R.id.phoneInput);
        emailTextInput = findViewById(R.id.emailInput);
        bPhoneTextInput = findViewById(R.id.backUpPhone);
        bEmialTextInput = findViewById(R.id.backUpEmail);

        editName = findViewById(R.id.editName);
        editDate = findViewById(R.id.editDate);
        editTitle = findViewById(R.id.editTitle);
        editPhone = findViewById(R.id.editPhone);
        editBphone = findViewById(R.id.editBackUpPhone);
        editEmail = findViewById(R.id.editEmail);
        editBemail = findViewById(R.id.editBackUpEmail);


        mStorageRef = FirebaseStorage.getInstance().getReference();


        Bundle extras = getIntent().getExtras();
        //get data from last activity
        if (extras != null) {

            //get video uri
            while (extras.getString("videoUriList") != null && extras.getString("videoThumbList")!= null ) {
                uri = Uri.parse(extras.getString("videoUriList"));
                Log.e("TAG", "URI: " + uri);
                //videoUriList.add(uri);

                videoListClass.setVideoUri(uri);


                //get video thumbnail
                Bitmap bmp = null;

                String filename = getIntent().getStringExtra("videoThumbList");
                try {
                    FileInputStream is = this.openFileInput(filename);
                    bmp = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //set thumbnail
                videoListClass.setBitmap(bmp);
                Log.e("TAG", "bmp: " + bmp);

                classList.add(videoListClass);

            }
            //get bitmap extra
            while (extras.getString("pictureStringList") != null){
                Bitmap bmp = null;

                String filename = getIntent().getStringExtra("pictureStringList");
                try {
                    FileInputStream is = this.openFileInput(filename);
                    bmp = BitmapFactory.decodeStream(is);
                    is.close();
                    Log.e("TAG", "bmp.tostring: " + bmp.toString());

                    pictureBitList.add(bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            //get picture bitmaps
            //pictureList.add(pictureBitmap)


        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String rName = recipientName.getEditText().getText().toString();
                final String sendDate = receiveDate.getEditText().getText().toString();
                final String title = videoTitle.getEditText().getText().toString();
                final String phone = phoneTextInput.getEditText().getText().toString();
                final String backupPhone = bPhoneTextInput.getEditText().getText().toString();
                final String email = emailTextInput.getEditText().getText().toString();
                final String backupEmail = bEmialTextInput.getEditText().getText().toString();


                if(!TextUtils.isEmpty(rName) && !TextUtils.isEmpty(sendDate) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(backupPhone)&& !TextUtils.isEmpty(backupEmail)) {
                    // send data to user.uid.uploads

                    //Firebase instances
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    mAuth = FirebaseAuth.getInstance();

                    //current user ID
                    currentUserId = mAuth.getCurrentUser().getUid();


                    // this uuid is how we identify a users video between the realtime database and the users cloud storage
                    final UUID randUUID = UUID.randomUUID();
                    final UUID vidUUID = UUID.randomUUID();
                    final UUID picUUID = UUID.randomUUID();



                    /*****************************
                     *
                     * THIS UPLOADS THIS VIDEO TO THE CURRENT USERS CLOUD STORAGE
                     * PATH: CURRENTUID/UPLOADS/RANDOMUUID
                     *
                     ******************************
                    StorageReference videoRef = mStorageRef.child(currentUserId + "/uploads/" + randUUID);

                    UploadTask uploadTask = videoRef.putFile(uri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Log.d("GOOD", "Shits good");
                            //Uri downloadUrl = taskSnapshot;

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            Log.d("ERROR", "broken");
                            errorCode = 1;
                        }
                    });
                     */

                    /*****************************
                     *
                     * this uploads extra info to the realtime database
                     * Path: UID/Uploads
                     *
                     *
                     ******************************/

                    databaseReference.child("Users").child(currentUserId).child("TimeCapsule").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(randUUID.toString())) {

                                //hash map for the database extra message info
                                Map videoAddMap = new HashMap();
                                Map pictureAddMap = new HashMap();
                                Map RecipientAddMap = new HashMap();
                                Map capsuleMap = new HashMap();


                                //while(!classList.isEmpty()) {
                                //testing data
                                Log.e("TAG", "classListSize: " + classList.size());
                                Log.e("TAG", "pictureListSize: " + pictureBitList.size());

                                //loop to through list of videos map it to database
                                for(int x = 0; x<classList.size(); x++){

                                    //videoAddMap.put("title", title);
                                    videoAddMap.put("vidID", vidUUID);
                                    videoAddMap.put("bitmap",classList.get(x).getBitmap());
                                    Log.e("TAG", "ClassList.getXgetBitmap: " + classList.get(x).getBitmap());

                                    videoAddMap.put("Uri", classList.get(x).getVideoUri());
                                    Log.e("TAG", "ClassList.getXgetVideoURI: " + classList.get(x).getVideoUri());

                                    videoAddMap.put("UUID", randUUID.toString());
                                    //videoAddMap.put("recipient", name);
                                    //videoAddMap.put("date", date);

                                    capsuleMap.put("Users/" + "/" + currentUserId + "/" + "TimeCapsule/" + randUUID.toString() + "/Videos" , videoAddMap);

                                }

                                //loop through list of pictures map to database
                                for( int x = 0; x<pictureBitList.size(); x++){
                                    //pictureAddMap.put("title", pTitle);
                                    pictureAddMap.put("picID", picUUID);
                                    pictureAddMap.put("pic", pictureBitList.get(x));
                                    Log.e("TAG", "pictureBitList.getX: " + pictureBitList.get(x));

                                    pictureAddMap.put("UUID", randUUID.toString());

                                    capsuleMap.put("Users/" + "/" + currentUserId + "/" + "TimeCapsule/" + randUUID.toString() + "/Pictures" , pictureAddMap);

                                }

                                //set recipient and map
                                RecipientAddMap.put("name", rName);
                                RecipientAddMap.put("phone", phone);
                                RecipientAddMap.put("backupPhone", backupPhone);
                                RecipientAddMap.put("email", email);
                                RecipientAddMap.put("backupEmail", backupEmail);
                                //RecipientAddMap.put("address", address);
                                RecipientAddMap.put("sendDate", sendDate);
                                RecipientAddMap.put("UUID", randUUID.toString());

                                capsuleMap.put("Users/" + "/" + currentUserId + "/" + "TimeCapsule/" + randUUID.toString() + "/Recipient" , RecipientAddMap);

                                //error checking
                                databaseReference.updateChildren(capsuleMap, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if(databaseError != null) {
                                            Log.d("CHAT_LOG", databaseError.getMessage());
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                        //clear text after submit
                        editName.setText("");
                        editDate.setText("");
                        editTitle.setText("");
                        editPhone.setText("");
                        editBphone.setText("");
                        editEmail.setText("");
                        editBemail.setText("");

                        if( errorCode == 0) {
                            Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Upload unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

            }

        });

    }

}
