package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyMembersActivity extends Activity implements Callback {
  String[] names = {"Karna", "Rama Krishna", "RK", "Prem"};
  int familyIndex = 0;
  private ListView familyMembersListView;
  private User[] users;
  private TextView name;
  private String[] familyMembersIdsList;
  private String[] familyMembersList;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.family_members_layout);
    familyMembersListView = (ListView) findViewById(R.id.family_members_listView);
    getActionBar().setTitle("Family Members");
    familyMembersIdsList = getIntent().getStringArrayExtra("family_members");
    if (familyMembersIdsList != null && familyMembersIdsList.length > 0) {
      for (String family_members : familyMembersIdsList)
        new WebserviceHelper(this, FamilyMembersActivity.this, "user").execute(
            "https://aesop.azure-mobile.net/tables/user?" +
                "$filter=(id+eq+'" + family_members + "')");
    } else {
      Toast toast = Toast.makeText(this, "No Family Members", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  @Override
  public void userCallBack(String json) {
    if (json != null) {
      users = new Gson().fromJson(json, User[].class);
      if (users.length > 0) {
        familyMembersList[familyIndex++] = users[0].getName();
      }
      if (familyIndex == familyMembersIdsList.length)
        populateFamilyMembers(familyMembersList);
    }
  }

  private void populateFamilyMembers(String[] familyMembersIdsList) {
    familyMembersListView.setAdapter(new FamilyMembersAdapter(getApplicationContext(), familyMembersList));
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
