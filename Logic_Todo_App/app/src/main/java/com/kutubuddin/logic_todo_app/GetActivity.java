package com.kutubuddin.logic_todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kutubuddin.logic_todo_app.adapter.TodoAdapter;
import com.kutubuddin.logic_todo_app.api.TodoApi;
import com.kutubuddin.logic_todo_app.apiClient.ApiClient;
import com.kutubuddin.logic_todo_app.model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetActivity extends AppCompatActivity {

    private RecyclerView todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        // Initialize RecyclerView
        todoList = findViewById(R.id.todoListRecyclerView);
        todoList.setLayoutManager(new LinearLayoutManager(this));

        // Load Todo items
        loadTodos();

        // FloatingActionButton to navigate to PostActivity
        FloatingActionButton fabPost = findViewById(R.id.fabAddTodo);
        fabPost.setOnClickListener(view -> {
            Intent intent = new Intent(GetActivity.this, PostActivity.class);
            startActivity(intent);
        });
    }

    // Method to fetch and display Todo items
    private void loadTodos() {
        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);
        Call<List<TodoModel>> call = todoApi.getTodo();

        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TodoModel> todoModelList = response.body();
                    setupRecyclerView(todoModelList);
                } else {
                    Toast.makeText(GetActivity.this, "Failed to load todos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Toast.makeText(GetActivity.this, "Failed to load todos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Set up RecyclerView with the TodoAdapter
    private void setupRecyclerView(List<TodoModel> todoListData) {
        todoAdapter = new TodoAdapter(todoListData, GetActivity.this, this::onDeleteClick, this::onEditClick);
        todoList.setAdapter(todoAdapter);
    }

    // Handle delete button click
    private void onDeleteClick(int id) {
        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);
        Call<Void> call = todoApi.deleteTodo(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GetActivity.this, "Todo deleted successfully", Toast.LENGTH_SHORT).show();
                    loadTodos(); // Refresh the list after deletion
                } else {
                    Toast.makeText(GetActivity.this, "Failed to delete todo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GetActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle edit button click
    private void onEditClick(TodoModel todo) {
        Intent intent = new Intent(GetActivity.this, UpdateActivity.class);
        intent.putExtra("todo_id", todo.getId());
        intent.putExtra("todo_title", todo.getTitel());
        intent.putExtra("todo_description", todo.getDescription());
        intent.putExtra("todo_type", todo.getTodotype());
        intent.putExtra("todo_priority", todo.getPriority());
        intent.putExtra("todo_date", todo.getDate());
        startActivity(intent);
    }

}

