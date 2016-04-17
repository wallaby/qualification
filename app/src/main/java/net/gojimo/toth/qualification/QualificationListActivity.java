package net.gojimo.toth.qualification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.gojimo.toth.qualification.model.Qualification;
import net.gojimo.toth.qualification.model.Subject;


public class QualificationListActivity extends AppCompatActivity
    implements QualificationListFragment.OnListFragmentInteractionListener,
    SubjectFragment.OnListFragmentInteractionListener {

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
    if (item.getSubjects().isEmpty()) {
      Toast.makeText(this, R.string.no_subject_in_qualification_msg, Toast.LENGTH_SHORT).show();
      return;
    }
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(R.id.activity_qualification_list, SubjectFragment.newInstance(1, item.getId()));
    ft.addToBackStack(item.getId());
    ft.commit();
  }

  @Override
  public void onListFragmentInteraction(Subject item) {

  }
}
