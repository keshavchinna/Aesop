package com.example.HomeInventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements Callback {

  EditText inputKey;
  private ProgressBar authentiCationProgressBar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    inputKey = (EditText) findViewById(R.id.pin_input);
    authentiCationProgressBar = (ProgressBar) findViewById(R.id.authenticate_progress);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    applyActionOnDone();
  }

  private void applyActionOnDone() {
    inputKey.setOnEditorActionListener(new EditText.OnEditorActionListener() {
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
    });
  }

  @Override
  public void userCallBack(String json) {
    if (json != null) {
      User[] users = new Gson().fromJson(json, User[].class);
      if (users.length > 0) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", new Gson().toJson(users[0]));
        intent.putExtra("userID", users[0].getId());
        authentiCationProgressBar.setVisibility(View.GONE);
        startActivity(intent);
      } else {
        Toast toast = Toast.makeText(this, "Please Check Your PIN Number", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        authentiCationProgressBar.setVisibility(View.GONE);
      }
    } else {
      authentiCationProgressBar.setVisibility(View.GONE);
      Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
    }
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
