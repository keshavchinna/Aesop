package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyMembersActivity extends Activity {
  String[] names = {"Karna", "Rama Krishna", "RK", "Prem"};
  private ListView familyMembersList;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.family_members_layout);
    familyMembersList = (ListView) findViewById(R.id.family_members_listView);
    familyMembersList.setAdapter(new FamilyMembersAdapter(getApplicationContext()));
  }

  private class FamilyMembersAdapter extends BaseAdapter {
    Context context;

    public FamilyMembersAdapter(Context applicationContext) {
      context = applicationContext;
    }

    @Override
    public int getCount() {
      return names.length;
    }

    @Override
    public Object getItem(int position) {
      return names[position];
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.family_members_child_layout, null);
      TextView name = (TextView) view.findViewById(R.id.family_member_name);
      name.setText(names[position]);
      return view;
    }
  }
}
