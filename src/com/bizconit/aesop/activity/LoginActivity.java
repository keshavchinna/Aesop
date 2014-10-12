package com.bizconit.aesop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bizconit.aesop.R;
import com.bizconit.aesop.helper.Callback;
import com.bizconit.aesop.helper.WebserviceHelper;
import com.bizconit.aesop.model.Inventory;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements Callback {

    EditText inputKey;
    private ProgressBar authentiCationProgressBar;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inputKey = (EditText) findViewById(R.id.pin_input);
        loginButton = (Button) findViewById(R.id.login_button);
        authentiCationProgressBar = (ProgressBar) findViewById(R.id.authenticate_progress);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        applyActionOnDone();
    }

    private void applyActionOnDone() {
        /**
         * if your using mobile uncomment below setOnEditorActionListener
         * and comment below setOnClickListener
         */

    /*inputKey.setOnEditorActionListener(new EditText.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == EditorInfo.IME_ACTION_DONE)) {
          String input = inputKey.getText().toString();
          if (input.length() < 4) {
            inputKey.setError("Enter Four Digit PIN Number");
          } else {
            authentiCationProgressBar.setVisibility(View.VISIBLE);
            new WebserviceHelper(getApplicationContext(), LoginActivity.this, "user").execute(
                "https://aesop.azure-mobile.net/tables/user?" +
                    "$filter=(pin+eq+'" + input + "')");
          }
        }
        return true;
      }
    });*/

        /**
         * if your using emulator uncomment below setOnClickListener
         * and comment above setOnEditorActionListener
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputKey.getText().toString();
                if (input.length() < 4) {
                    inputKey.setError("Enter Four Digit PIN Number");
                    authentiCationProgressBar.setVisibility(View.GONE);
                } else {
                    Log.d("AesopTest", "user pin: " + input);
                    authentiCationProgressBar.setVisibility(View.VISIBLE);
                    new WebserviceHelper(getApplicationContext(), LoginActivity.this, "user").execute(
                            "https://aesop.azure-mobile.net/tables/user?" +
                                    "$filter=(pin+eq+'" + input + "')");
                }
            }
        });
    }

    @Override
    public void userCallBack(String json) {
        if (json != null) {
            Inventory.User[] users = new Gson().fromJson(json, Inventory.User[].class);
            if (users.length > 0) {
                Log.d("AesopTest", "user length: " + users.length);
                callDashboardActivity(users[0]);
            } else {
                inputKey.setError("Invalid PIN Number");
                inputKey.setText("");
                authentiCationProgressBar.setVisibility(View.GONE);
            }
        } else {
            authentiCationProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
        }
    }

    private void callDashboardActivity(Inventory.User user) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra("user", new Gson().toJson(user));
        intent.putExtra("userName", user.getName());
        intent.putExtra("userID", user.getId());
        authentiCationProgressBar.setVisibility(View.GONE);
        startActivity(intent);
    }

    @Override
    public void smartHubCallBack(String o) {
    }

    @Override
    public void inventoryCallBack(String o) {
    }

    @Override
    public void sensorCallBack(String o) {
    }
}
