package net.gojimo.toth.qualification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gojimo.toth.qualification.QualificationListFragment.OnListFragmentInteractionListener;
import net.gojimo.toth.qualification.model.Qualification;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Qualification} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class QualificationRecyclerViewAdapter extends RecyclerView.Adapter<QualificationRecyclerViewAdapter.ViewHolder> {

  private final List<Qualification> items;
  private final OnListFragmentInteractionListener listener;

  public QualificationRecyclerViewAdapter(List<Qualification> items, OnListFragmentInteractionListener listener) {
    this.items = items;
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.qualification_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.item = items.get(position);
    holder.nameView.setText(items.get(position).getName());

    holder.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != listener) {
          // Notify the active callbacks interface (the activity, if the
          // fragment is attached to one) that an item has been selected.
          listener.onListFragmentInteraction(holder.item);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView nameView;
    public Qualification item;

    public ViewHolder(View view) {
      super(view);
      this.view = view;
      nameView = (TextView) view.findViewById(R.id.name);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + nameView.getText() + "'";
    }
  }
}
