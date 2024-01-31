package pl.edu.pb.sm_projekt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// PomoAdapter.java
public class PomoAdapter extends RecyclerView.Adapter<PomoAdapter.PomoViewHolder> {

    private final List<PomoItem> pomoList;

    public PomoAdapter(List<PomoItem> pomoList) {
        this.pomoList = pomoList;
    }

    @NonNull
    @Override
    public PomoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pomo_item, parent, false);
        return new PomoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PomoViewHolder holder, int position) {
        PomoItem pomoItem = pomoList.get(position);
        holder.titleTextView.setText(pomoItem.getTitle());
        holder.durationTextView.setText(String.valueOf(pomoItem.getDuration()));
    }

    @Override
    public int getItemCount() {
        return pomoList.size();
    }

    public static class PomoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView durationTextView;

        public PomoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            durationTextView = itemView.findViewById(R.id.item_duration);
        }
    }
}
