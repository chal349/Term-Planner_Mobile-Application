package com.example.termplanner.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.UI.CourseDetailsActivity;
import com.example.termplanner.UI.TermDetailsActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;

     //   private final status;
     //   private String instructorName;
     //   private String instructorPhone;
      //  private String instructorEmail;
     //   private String noteTitle;
      //  private String noteContent;

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
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("termStartDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Course> courseList;

    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
            holder.title.setText("No product name");
            holder.date.setText("no date");
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
