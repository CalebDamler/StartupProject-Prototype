package com.calebco.caleb.prototype1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/************************************************************
 * Handle login or sign up screen
 *
 ************************************************************/
public class CheckAccountActivity extends AppCompatActivity {

    private Button logRegBtn;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_account);
        button = findViewById(R.id.button3);
        logRegBtn = findViewById(R.id.logRegBtn);
        logRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if no account go to create account
                Intent intent = new Intent(CheckAccountActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if have account go to login page
                Intent intent = new Intent(CheckAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
