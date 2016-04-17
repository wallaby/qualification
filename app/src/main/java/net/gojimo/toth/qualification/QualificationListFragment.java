package net.gojimo.toth.qualification;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gojimo.toth.qualification.model.Qualification;
import net.gojimo.toth.qualification.util.GojimoServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class QualificationListFragment extends Fragment {

  private static final String ARG_COLUMN_COUNT = "column-count";
  private static final String LOGGER_TAG = "QualificationFragment";

  private int columnCount = 1;
  private OnListFragmentInteractionListener listener;
  private final List<Qualification> qualifications = new ArrayList<>();
  private RecyclerView.Adapter adapter;


  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public QualificationListFragment() {
  }

  public static QualificationListFragment newInstance(int columnCount) {
    QualificationListFragment fragment = new QualificationListFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    init();
    if (getArguments() != null) {
      columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  private void init() {
    new LoadQualifications().execute();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.qualification_list, container, false);

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      if (columnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
      }
      adapter = new QualificationRecyclerViewAdapter(qualifications, listener);
      recyclerView.setAdapter(adapter);
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

  public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(Qualification item);
  }

  class LoadQualifications extends AsyncTask<Void, String, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
      try {
        GojimoServer server = GojimoServer.getGojimoServer();
        qualifications.addAll(server.getQualifications());
        return true;
      } catch (IOException e) {
        Log.e(LOGGER_TAG, e.getMessage());
      }
      return false;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean result) {
      super.onPostExecute(result);
      adapter.notifyDataSetChanged();
    }
  }
}
