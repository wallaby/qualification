package net.gojimo.toth.qualification;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gojimo.toth.qualification.model.Qualification;
import net.gojimo.toth.qualification.model.Subject;
import net.gojimo.toth.qualification.util.GojimoServer;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SubjectFragment extends Fragment {

  private static final String ARG_QUALIFICATION_ID = "qualification-id";
  private static final String ARG_COLUMN_COUNT = "column-count";
  private static final String LOGGER_TAG = "SubjectFragment";

  private int columnCount = 1;
  private Qualification qualification;
  private OnListFragmentInteractionListener listener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public SubjectFragment() {
  }

  public static SubjectFragment newInstance(int columnCount, String qualificationId) {
    SubjectFragment fragment = new SubjectFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    args.putString(ARG_QUALIFICATION_ID, qualificationId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
      //TODO: remove dependency on qualification list being loaded
      qualification = GojimoServer.getGojimoServer().getQualification(getArguments().getString(ARG_QUALIFICATION_ID));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.subject_list, container, false);

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      if (columnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
      }
      List<Subject> subjects = qualification.getSubjects();
      recyclerView.setAdapter(new SubjectRecyclerViewAdapter(subjects, listener));
    }
    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(String.format(getString(R.string.subject_list_title), qualification.getName()));
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListFragmentInteractionListener) {
      listener = (OnListFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(Subject item);
  }
}
