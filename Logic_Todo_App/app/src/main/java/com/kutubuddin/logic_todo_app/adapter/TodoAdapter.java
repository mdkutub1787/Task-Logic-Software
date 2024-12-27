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

    // Constructor with listener for delete action
    public TodoAdapter(List<TodoModel> todoList, Context context, OnDeleteClickListener onDeleteClickListener) {
        this.todoList = todoList;
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each Todo item
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views for each Todo item
        TodoModel todo = todoList.get(position);
        holder.textTitle.setText(todo.getTitel());
        holder.textDescription.setText(todo.getDescription());
        holder.textTodotype.setText(todo.getTodotype());
        holder.textPriority.setText(todo.getPriority());
        holder.textDate.setText(todo.getDate());

        // Set up the delete button click listener
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(todo.getId()));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    // Interface for handling delete button click events
    public interface OnDeleteClickListener {
        void onDeleteClick(int id);
    }

    // ViewHolder to hold references to views in each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription, textTodotype, textPriority, textDate;
        View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textDescription = itemView.findViewById(R.id.description);
            textTodotype = itemView.findViewById(R.id.todoType);
            textPriority = itemView.findViewById(R.id.priority);
            textDate = itemView.findViewById(R.id.date);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
