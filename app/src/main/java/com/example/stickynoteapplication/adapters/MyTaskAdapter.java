package com.example.stickynoteapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.entities.MyTaskEntities;

import java.util.ArrayList;
import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private List<MyTaskEntities> tasks  = new ArrayList<>();
    private final TaskActionListener listener;

    public MyTaskAdapter(TaskActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_preview, parent, false);  // Ensure this matches your XML layout name
        return new MyTaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int position) {
        if (tasks != null && !tasks.isEmpty()) {
            MyTaskEntities task = tasks.get(position);

            // Clear previous task views
            holder.taskContainer.removeAllViews();

            // Add the task to the container
            View taskView = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.task_item_preview, holder.taskContainer, false);
            TextView textNote = taskView.findViewById(R.id.prev_task_description);
            textNote.setText(task.getTaskDescription());
            holder.taskContainer.addView(taskView);

            // Set up click listeners
            holder.itemView.setOnClickListener(v -> listener.onTaskClick(position));
            holder.deleteButton.setOnClickListener(v -> listener.onDeleteTask(tasks.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void setTasks(List<MyTaskEntities> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public List<MyTaskEntities> getTaskEntitiesList() {
        return tasks;
    }

    public class MyTaskViewHolder extends RecyclerView.ViewHolder {
        LinearLayout taskContainer;
        ImageButton deleteButton;

        public MyTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskContainer = itemView.findViewById(R.id.task_container);
            deleteButton = itemView.findViewById(R.id.delete_button);

        }
    }

    public interface TaskActionListener {
        void onTaskClick(int position);
        void onDeleteTask(MyTaskEntities task);
        void onTaskUpdated(MyTaskEntities task);
    }
}


//public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.TaskViewHolder> {
//
//    private List<MyTaskEntities> taskEntitiesList = new ArrayList<>();
//    private TaskActionListener taskActionListener;
//
//    // Interface to handle task actions (delete, update)
//    public interface TaskActionListener {
//        void onTaskClick(int position);
//        void onDeleteTask(MyTaskEntities task);  // Task deletion action
//        void onTaskUpdated(MyTaskEntities task);  // Task update action (e.g., completed or description change)
//    }
//
//    public MyTaskAdapter(TaskActionListener listener) {
//        this.taskActionListener = listener;  // Set listener for task actions
//    }
//
//    public void setTasks(List<MyTaskEntities> taskEntitiesList) {
//        this.taskEntitiesList = taskEntitiesList;
//        notifyDataSetChanged();
//    }
//
//    public List<MyTaskEntities> getTaskEntitiesList() {
//        return taskEntitiesList;
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.todolist_item, parent, false);
//
//        return new TaskViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//        MyTaskEntities task = taskEntitiesList.get(position);
//        holder.bind(task, taskActionListener);  // Bind the task data and listener
//    }
//
//    @Override
//    public int getItemCount() {
//        return taskEntitiesList.size();
//    }
//
//    static class TaskViewHolder extends RecyclerView.ViewHolder {
//
//        private EditText taskTitle;
//        CheckBox taskCheckbox;
//        ImageView deleteTaskButton;
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//            taskTitle = itemView.findViewById(R.id.task_content);
//            taskCheckbox = itemView.findViewById(R.id.todo_item_checkbox);
//            deleteTaskButton = itemView.findViewById(R.id.delete_item_button);
//        }
//
//        public void bind(MyTaskEntities task, TaskActionListener listener) {
//            // Set task description
//            taskTitle.setText(task.getTaskDescription());
//            taskCheckbox.setChecked(task.isCompleted());
//
//            // Update task completion state when the checkbox is clicked
//            taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                task.setCompleted(isChecked);
//                listener.onTaskUpdated(task);  // Notify listener of task update
//            });
//
//            // Update task description when edited
//            taskTitle.setOnFocusChangeListener((v, hasFocus) -> {
//                if (!hasFocus) {
//                    task.setTaskDescription(taskTitle.getText().toString());
//                    listener.onTaskUpdated(task);  // Notify listener of task update
//                }
//            });
//
//            // Handle task deletion
//            deleteTaskButton.setOnClickListener(v -> listener.onDeleteTask(task));  // Notify listener of task deletion
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onTaskClick(getAdapterPosition());  // Notify listener of task click
//                }
//            });
//        }
//    }
//}

//package com.example.stickynoteapplication.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.entities.MyTaskEntities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.TaskViewHolder> {
//
//    private List<MyTaskEntities> taskEntitiesList = new ArrayList<>();
//
//    public List<MyTaskEntities> getTaskEntitiesList() {
//        return taskEntitiesList;
//    }
//
//    public void setTasks(List<MyTaskEntities> taskEntitiesList) {
//        this.taskEntitiesList = taskEntitiesList;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_task, parent, false);
//
//        return new TaskViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//        holder.bind(taskEntitiesList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return taskEntitiesList.size();
//    }
//
//    static class TaskViewHolder extends RecyclerView.ViewHolder {
//
//        private EditText taskTitle;
//        CheckBox taskCheckbox;
//        ImageView deleteTaskButton;
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//            taskTitle = itemView.findViewById(R.id.todo_list_name_input);
//            taskCheckbox = itemView.findViewById(R.id.todo_item_checkbox);
//            deleteTaskButton = itemView.findViewById(R.id.delete_button);
//        }
//
//        public void bind(MyTaskEntities task) {
//            taskTitle.setText(task.getTaskDescription());  // Bind the task name to the CheckBox text
//            taskTitle.setPressed(task.isCompleted());
//            //taskTitle.setAlpha(task.isCompleted() ? 0.5f : 1.0f);
//            deleteTaskButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Add task deletion logic here (use TaskViewModel to delete)
//                }
//            });
//        }
//    }
//}
