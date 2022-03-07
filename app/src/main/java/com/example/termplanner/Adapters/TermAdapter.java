package com.example.termplanner.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.UI.TermDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> implements Filterable {


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.term_list_title);
            date = itemView.findViewById(R.id.term_date);

            CardView termListView = itemView.findViewById(R.id.term_list_view);
            termListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = termsList.get(position);
                    Intent intent = new Intent(context, TermDetailsActivity.class);
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("termTitle", current.getTitle());
                    intent.putExtra("termStartDate", current.getStartDate());
                    intent.putExtra("termEndDate", current.getEndDate());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Term> termsList;
    private List<Term> termsListFull;

    public TermAdapter(Context context, List<Term> termsList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.termsList = termsList;
        termsListFull = new ArrayList<>(termsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_term, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(termsList != null){
        final Term current = termsList.get(position);
        holder.title.setText(current.getTitle());
        holder.date.setText(current.getStartDate() + " - " + current.getEndDate());
    }
    else{
        holder.title.setText(" ");
        holder.date.setText(" ");
    }
}

    public void setTerms(List<Term> terms) {
        termsList = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return termsList.size();
    }

    @Override
    public Filter getFilter() {
        return termFilter;
    }

    private Filter termFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Term> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(termsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Term term : termsListFull){
                    if(term.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(term);
                    }
                }
            } FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            termsList.clear();
            termsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
