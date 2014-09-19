package com.example.HomeInventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements Callback {

  EditText inputKey;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    inputKey = (EditText) findViewById(R.id.pin_input);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    applyActionOnDone();
  }

  private void applyActionOnDone() {
    inputKey.setOnEditorActionListener(new EditText.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == EditorInfo.IME_ACTION_DONE)) {
          String input = inputKey.getText().toString();
          if (input == null || input.length() < 4) {
            inputKey.setError("Input shouln't less than 4 letters");
          } else {
            new WebserviceHelper(LoginActivity.this).execute(
                "http://premapp.azure-mobile.net/tables/user?" +
                    "$filter=(pin+eq+'" + input + "')");
          }
        }
        return true;
      }
    });
  }

  @Override
  public void callback(String json) {
    if (json != null) {
      User[] users = new Gson().fromJson(json, User[].class);
      if (users.length > 0) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", new Gson().toJson(users[0]));
        startActivity(intent);
      }

    } else {
      Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
    }
  }
}
