package com.kutubuddin.logic_todo_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kutubuddin.logic_todo_app.R;
import com.kutubuddin.logic_todo_app.model.TodoModel;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todoList;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

    // Constructor with listener for delete and edit actions
    public TodoAdapter(List<TodoModel> todoList, Context context,
                       OnDeleteClickListener onDeleteClickListener,
                       OnEditClickListener onEditClickListener) {
        this.todoList = todoList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoModel todo = todoList.get(position);
        holder.textTitle.setText(todo.getTitel());
        holder.textDescription.setText(todo.getDescription());
        holder.textTodotype.setText(todo.getTodotype());
        holder.textPriority.setText(todo.getPriority());
        holder.textDate.setText(todo.getDate());

        // Set up the delete button click listener
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(todo.getId()));

        // Set up the edit button click listener
        holder.editButton.setOnClickListener(v -> onEditClickListener.onEditClick(todo));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    // Interface for handling delete button click events
    public interface OnDeleteClickListener {
        void onDeleteClick(int id);
    }

    // Interface for handling edit button click events
    public interface OnEditClickListener {
        void onEditClick(TodoModel todo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription, textTodotype, textPriority, textDate;
        View deleteButton, editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textDescription = itemView.findViewById(R.id.description);
            textTodotype = itemView.findViewById(R.id.todoType);
            textPriority = itemView.findViewById(R.id.priority);
            textDate = itemView.findViewById(R.id.date);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
