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

import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.UI.CourseDetailsActivity;
import com.example.termplanner.UI.TermDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements Filterable {



    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.course_list_title);
            date = itemView.findViewById(R.id.course_list_date);

            CardView courseListView = itemView.findViewById(R.id.course_list_view);
            courseListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseStatusSelection", current.getCourseStatusSelection());
                    intent.putExtra("courseNotes", current.getNoteContent());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Course> courseList;
    private List<Course> courseListFull;

    public CourseAdapter(Context context, List<Course> courseList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courseList = courseList;
        courseListFull = new ArrayList<>(courseList);
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_course, parent, false);
        return new CourseAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        if(courseList != null){
            final Course current = courseList.get(position);
            holder.title.setText(current.getCourseTitle());
            holder.date.setText(current.getCourseStartDate() + " - " + current.getCourseEndDate());
        }
        else{
            holder.title.setText(" ");
            holder.date.setText(" ");
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

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    private Filter courseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Course> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(courseListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Course course : courseListFull){
                    if(course.getCourseTitle().toLowerCase().contains(filterPattern) || course.getCourseStartDate().contains(filterPattern) || course.getCourseEndDate().contains(filterPattern)){
                        filteredList.add(course);
                    }
                }
            } FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            courseList.clear();
            courseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
