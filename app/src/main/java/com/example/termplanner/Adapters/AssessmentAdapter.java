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

import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.UI.AssessmentAddDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> implements Filterable {



    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;
        private final TextView type;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assessment_list_title);
            date = itemView.findViewById(R.id.assessment_list_date);
            type = itemView.findViewById(R.id.assessment_type);

            CardView assessmentListView = itemView.findViewById(R.id.assessment_list_view);
            assessmentListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = assessmentList.get(position);
                    Intent intent = new Intent(context, AssessmentAddDetailsActivity.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Assessment> assessmentList;
    private List<Assessment> assessmentListFull;

    public AssessmentAdapter(Context context, List<Assessment> assessmentList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.assessmentList = assessmentList;
        assessmentListFull = new ArrayList<>(assessmentList);
    }

    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_assessment, parent, false);
        return new AssessmentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        if(assessmentList != null){
            final Assessment current = assessmentList.get(position);
            holder.title.setText(current.getAssessmentTitle());
            holder.type.setText(current.getAssessmentType());
            holder.date.setText(current.getAssessmentStartDate() + " - " + current.getAssessmentEndDate());
        }
        else{
            holder.title.setText(" ");
            holder.type.setText(" ");
            holder.date.setText(" ");
        }
    }

    public void setAssessments(List<Assessment> courses) {
        assessmentList = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    @Override
    public Filter getFilter() {
        return assessmentFilter;
    }

    private Filter assessmentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Assessment> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(assessmentListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Assessment assessment : assessmentListFull){
                    if(assessment.getAssessmentTitle().toLowerCase().contains(filterPattern) || assessment.getAssessmentStartDate().contains(filterPattern) || assessment.getAssessmentEndDate().contains(filterPattern)){
                        filteredList.add(assessment);
                    }
                }
            } FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            assessmentList.clear();
            assessmentList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
