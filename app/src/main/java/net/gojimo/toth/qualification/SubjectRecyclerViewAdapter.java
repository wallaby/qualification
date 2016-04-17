package net.gojimo.toth.qualification;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gojimo.toth.qualification.model.Subject;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Subject} and makes a call to the
 * specified {@link SubjectFragment.OnListFragmentInteractionListener}.
 */
public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.ViewHolder> {

  private final List<Subject> values;
  private final SubjectFragment.OnListFragmentInteractionListener listener;

  public SubjectRecyclerViewAdapter(List<Subject> items, SubjectFragment.OnListFragmentInteractionListener listener) {
    values = items;
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.subject_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.item = values.get(position);
    holder.titleView.setText(values.get(position).getTitle());
    holder.cardView.setCardBackgroundColor(Color.parseColor(values.get(position).getColour()));

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
    return values.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final CardView cardView;
    public final TextView titleView;
    public Subject item;

    public ViewHolder(View view) {
      super(view);
      this.view = view;
      titleView = (TextView) view.findViewById(R.id.subject_list_item_title);
      cardView = (CardView) view.findViewById(R.id.subject_card_view);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + titleView.getText() + "'";
    }
  }
}
