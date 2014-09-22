package com.bizconit.aesop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.homeinventory.R;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyMembersActivity extends Activity implements Callback, AdapterView.OnItemClickListener {
  String[] names = {"Karna", "Rama Krishna", "RK", "Prem"};
  int familyIndex = 0;
  private ListView familyMembersListView;
  private User[] users;
  private TextView name;
  private String[] familyMembersIdsList;
  private String[] familyMembersList;
  private String[] userIds;
  private ProgressBar progressBar;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.family_members_layout);
    familyMembersListView = (ListView) findViewById(R.id.family_members_listView);
    progressBar = (ProgressBar) findViewById(R.id.family_loading);
    getActionBar().setTitle("Family Members");
    familyMembersIdsList = getIntent().getStringArrayExtra("family_members");
    familyMembersList = new String[familyMembersIdsList.length];
    userIds = new String[familyMembersIdsList.length];
    if (familyMembersIdsList != null && familyMembersIdsList.length > 0) {
      progressBar.setVisibility(View.VISIBLE);
      for (String family_members : familyMembersIdsList) {
        Log.d("test3", "ids:" + family_members);
        new WebserviceHelper(this, FamilyMembersActivity.this, "user").execute(
            "https://aesop.azure-mobile.net/tables/user?" +
                "$filter=(id+eq+'" + family_members + "')");
      }
    } else {
      Toast toast = Toast.makeText(this, "No Family Members", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  @Override
  public void userCallBack(String json) {
    Log.d("test3", "json:" + json);
    if (json != null) {
      users = new Gson().fromJson(json, User[].class);
      if (users.length > 0) {
        userIds[familyIndex] = users[0].getId();
        familyMembersList[familyIndex++] = users[0].getName();
      } else {
        familyIndex++;
      }
      if (familyIndex == familyMembersIdsList.length) {
        progressBar.setVisibility(View.GONE);
        populateFamilyMembers(familyMembersList);
      }
    } else {
      progressBar.setVisibility(View.GONE);
      Toast toast = Toast.makeText(this, "Problem Connecting to Server", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = new MenuInflater(getBaseContext());
    inflater.inflate(R.menu.dashboard_menu, menu);
    menu.getItem(0).setVisible(false);
    menu.getItem(1).setVisible(false);
    return true;
  }

  private void populateFamilyMembers(String[] familyMembersIdsList) {
    familyMembersListView.setAdapter(new FamilyMembersAdapter(getApplicationContext(), familyMembersList));
    familyMembersListView.setOnItemClickListener(this);
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

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent intent = new Intent(getApplicationContext(), ShowFamilyMemberData.class);
    intent.putExtra("userID", userIds[position]);
    intent.putExtra("familyMemberName", familyMembersList[position]);
    startActivity(intent);
  }

  private class FamilyMembersAdapter extends BaseAdapter {
    private final String[] familyMembersNames;
    Context context;

    public FamilyMembersAdapter(Context applicationContext, String[] familyMembersNames) {
      context = applicationContext;
      this.familyMembersNames = familyMembersNames;
    }

    @Override
    public int getCount() {
      return familyMembersNames.length;
    }

    @Override
    public Object getItem(int position) {
      return familyMembersNames[position];
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.family_members_child_layout, null);
      name = (TextView) view.findViewById(R.id.family_member_name);
      name.setText(familyMembersNames[position]);
      return view;
    }
  }
}
