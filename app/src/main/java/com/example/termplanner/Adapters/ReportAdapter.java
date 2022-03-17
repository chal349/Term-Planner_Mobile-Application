package com.example.termplanner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;
        private final TextView createDate;


        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.report_title);
            date = itemView.findViewById(R.id.report_date);
            createDate = itemView.findViewById(R.id.report_create_date);
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Course> courseList;

    public ReportAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_report, parent, false);
        return new ReportAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        if(courseList != null){
            final Course current = courseList.get(position);
            holder.title.setText(current.getCourseTitle());
            holder.date.setText(current.getCourseStatus());
            holder.createDate.setText(current.getCreatedDate());
        }
        else{
            holder.title.setText(" ");
            holder.date.setText(" ");
            holder.createDate.setText(" ");
        }
    }

    public void setCourses(List<Course> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }


}

