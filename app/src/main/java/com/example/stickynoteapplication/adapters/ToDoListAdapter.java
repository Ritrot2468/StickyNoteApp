package com.example.stickynoteapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.entities.MyTaskEntities;
import com.example.stickynoteapplication.viewmodels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private List<MyNoteEntities> notes;
    //private TaskViewModel taskViewModel;  // Initialize as needed

    public interface OnNoteClickListener {
        void onTodoClick(int position);
    }
    private OnNoteClickListener onNoteClickListener;
    private Context context;

    public ToDoListAdapter(Context context, OnNoteClickListener onNoteClickListener) {
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
        this.notes = new ArrayList<>();
    }
    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_item, parent, false);
        return new ToDoListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        if (notes != null && !notes.isEmpty()) {
            MyNoteEntities note = notes.get(position);
            holder.titleTextView.setText(note.getTitle());

            // Load tasks for this note
            TaskViewModel taskViewModel = new ViewModelProvider((FragmentActivity) holder.itemView.getContext()).get(TaskViewModel.class);
            taskViewModel.getTasksforNote(note.getId()).observe((LifecycleOwner) holder.itemView.getContext(), tasks -> {
                if (tasks != null) {
                    holder.updateTasks(tasks);
                }
            });

           // holder.te.setText(note.getDateTime());
        }
    }

    public List<MyNoteEntities> getNotes() {
        return notes;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<MyNoteEntities> notes) {
        this.notes = notes;
        notifyDataSetChanged();

    }

    public static class ToDoListViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        LinearLayout taskContainer;
        TextView textDateTime;
        ImageView imageNote;
        ImageButton deleteButton;

        //OnNoteClickListener onNoteClickListener;

        public ToDoListViewHolder(View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            taskContainer = itemView.findViewById(R.id.todo_items_container);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            imageNote = itemView.findViewById(R.id.imageNote_item);
            deleteButton = itemView.findViewById(R.id.delete_button);
            this.on = onNoteClickListener;
        }

        public void updateTasks(List<MyTaskEntities> tasks) {
            //taskContainer.removeAllViews();
            if (taskContainer == null) {
                // Log an error or handle this case
                Log.e("ToDoListViewHolder", "taskContainer is null");
                return;
            }

            taskContainer.removeAllViews();
            int maxTasks = Math.min(tasks.size(), 3);

            for (int i = 0; i < maxTasks; i++) {
                View taskView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.task_item_preview, taskContainer, false);
                TextView textNote = taskView.findViewById(R.id.prev_task_description);
               // CheckBox checkBox = taskView.findViewById(R.id.)
                textNote.setText(tasks.get(i).getTaskDescription());
                taskContainer.addView(taskView);
            }
        }

        @Override
        public void onClick(View v) {
            this.onNoteClickListener.onNoteClick(getAdapterPosition());  // Trigger the callback with the note position
        }

    }
}
