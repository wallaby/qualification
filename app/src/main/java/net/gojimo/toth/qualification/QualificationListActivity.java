package net.gojimo.toth.qualification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import net.gojimo.toth.qualification.model.Qualification;


public class QualificationListActivity extends AppCompatActivity
    implements QualificationListFragment.OnListFragmentInteractionListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_qualification_list);
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.activity_qualification_list);

    if (fragment == null) {
      fragment = new QualificationListFragment();
      fm.beginTransaction()
          .add(R.id.activity_qualification_list, fragment)
          .commit();
    }
  }

  @Override
  public void onListFragmentInteraction(Qualification item) {

  }
}
