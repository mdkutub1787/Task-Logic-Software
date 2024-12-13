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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private List<TodoModel> todoList;
    private Context context;

    public TodoAdapter(List<TodoModel> todoList, Context context) {
        this.todoList = todoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(com.kutubuddin.logic_todo_app.R.layout.todo_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TodoModel notification = todoList.get(position);
        holder.textTitle.setText(notification.getTitel());
        holder.textDescription.setText(notification.getDescription());
        holder.textTodotype.setText(notification.getTodotype());
        holder.textPriority.setText(notification.getPriority());
        holder.textDate.setText(notification.getDate());

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textDescription;
        TextView textTitle;
        TextView textTodotype;
        TextView textPriority;
        TextView textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.title);
            textDescription=itemView.findViewById(R.id.description);
            textTodotype=itemView.findViewById(R.id.todoType);
            textPriority=itemView.findViewById(R.id.priority);
            textDate=itemView.findViewById(R.id.date);
        }


    }

}
