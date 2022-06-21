package com.example.students.swipe;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.students.Edit_Project_By_Id;
import com.example.students.Project;
import com.example.students.R;

import java.util.ArrayList;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> {


    // Swipe Adapter
    public static int SelectedProjectId = 0;
    private RequestQueue _Queue;
    private Context context;
    private ArrayList<Project> students;
    public static String selectedImageURL;
    public static ArrayList<String> SelectedProjects = new ArrayList<>();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    //constructor
    public SwipeAdapter(Context context, ArrayList<Project> students){
        this.context = context;
        this.students = students;
    }


    //set each project with the student ID
    public void setStudents(ArrayList<Project> students){
        this.students = new ArrayList<>();
        this.students = students;
        notifyDataSetChanged();
    }

    //creative the recycler view's layout based on parent view
    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_projects, parent, false);
        return new SwipeViewHolder(view);
    }

    //bind view holders
    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {

        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipereveallayout, String.valueOf(students.get(position).getTitle()));
        Log.d(TAG, String.valueOf(students.get(position).getTitle()));
        viewBinderHelper.closeLayout(String.valueOf(students.get(position).getTitle()));
        holder.bindData(students.get(position));

        holder.textEdit.setOnClickListener(view -> updateProject(position));
        holder.textDelete.setOnClickListener(view -> deleteProject(position));
    }

    //count of items
    @Override
    public int getItemCount() {
        return students.size();
    }


    // View Holder

    class SwipeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textEdit;
        private TextView textDelete;
        private SwipeRevealLayout swipereveallayout;

        public  SwipeViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textViewProject);
            textEdit = itemView.findViewById(R.id.txtEdit);
            textDelete = itemView.findViewById(R.id.txtDelete);
            swipereveallayout = itemView.findViewById(R.id.swipelayout);

        }

        //bind data
        void bindData(Project student){
            textView.setText(student.getTitle());
        }
    }


    //update project
    public void updateProject(int position){
        Project clickedProject;
        clickedProject = students.get(position);

        SelectedProjects = new ArrayList<>();
        SelectedProjectId = clickedProject.getProjectId();
        //add item details
        SelectedProjects.add(String.valueOf(SelectedProjectId));
        SelectedProjects.add(clickedProject.getTitle());
        SelectedProjects.add(clickedProject.getDescription());
        SelectedProjects.add(String.valueOf(clickedProject.getYear()));
        SelectedProjects.add(clickedProject.getFirstName());
        SelectedProjects.add(clickedProject.getLastName());
        SelectedProjects.add(String.valueOf(clickedProject.getStudentId()));
        selectedImageURL = String.valueOf(clickedProject.getURL());

        Log.d(TAG, selectedImageURL);
        //open edit activity
        Intent edit_projectsIntent = new Intent(context, Edit_Project_By_Id.class);
        context.startActivity(edit_projectsIntent);

    }

    //delete project
    public void deleteProject(int position) {

        //opens a dialog
        AlertDialog diaBox = AskOption(position);
        diaBox.show();

    }

    //dialog event
    private AlertDialog AskOption(int position)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete the Project: " + students.get(position).getTitle())
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        _Queue = Volley.newRequestQueue(context);

                        Log.d(TAG, students.get(position).getTitle());

                        Project clickedProject = students.get(position);

                        //url for delete project
                        String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/students/";
                        url = url + String.valueOf(clickedProject.getProjectId());
                        SelectedProjectId = clickedProject.getProjectId();

                        Log.d(TAG, url);

                        // Request a string response from the provided URL. //request method is delete
                        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Notification to say you have deleted the project
                                        Toast.makeText(context, "Delete Finished", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Delete didn't Work", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //update list and dismiss dialog
                        _Queue.add(deleteRequest);
                        notifyItemRemoved(position);
                        notifyItemRangeRemoved(position, students.size());
                        dialog.dismiss();
                    }

                })
                //cancel button
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }

}
