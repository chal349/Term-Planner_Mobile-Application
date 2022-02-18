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
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("courseStatusSelection", current.getStatusSelection());
                    intent.putExtra("notes", current.getNoteContent());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
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
            holder.title.setText(current.getTitle());
            holder.date.setText(current.getStartDate() + " - " + current.getEndDate());
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
