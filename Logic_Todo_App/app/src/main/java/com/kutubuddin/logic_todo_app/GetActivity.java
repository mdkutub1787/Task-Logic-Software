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

        // Initialize RecyclerView and set its layout manager
        todoList = findViewById(R.id.todoListRecyclerView);
        todoList.setLayoutManager(new LinearLayoutManager(this));

        // Initialize TodoApi and make the GET request to fetch todo items
        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);
        Call<List<TodoModel>> call = todoApi.getTodo();

        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful()) {
                    List<TodoModel> todoModelList = response.body();
                    // Set up RecyclerView with the adapter
                    todoAdapter = new TodoAdapter(todoModelList, GetActivity.this, new TodoAdapter.OnDeleteClickListener() {
                        @Override
                        public void onDeleteClick(int id) {
                            // Call the delete method when delete button is clicked
                            deleteTodoById(id);
                        }
                    });
                    todoList.setAdapter(todoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Toast.makeText(GetActivity.this, "Failed to load todos", Toast.LENGTH_SHORT).show();
            }
        });

        // FloatingActionButton click listener to navigate to PostActivity
        FloatingActionButton fabPost = findViewById(R.id.fabAddTodo);
        fabPost.setOnClickListener(view -> {
            Intent intent = new Intent(GetActivity.this, PostActivity.class);
            startActivity(intent);
        });
    }

    // Method to delete a Todo by ID
    private void deleteTodoById(int id) {
        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);
        Call<Void> call = todoApi.deleteTodo(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Notify the user and update the list
                    Toast.makeText(GetActivity.this, "Todo deleted successfully", Toast.LENGTH_SHORT).show();
                    // Refresh the list
                    loadTodos();
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

    // Method to reload the Todo list after deletion
    private void loadTodos() {
        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);
        Call<List<TodoModel>> call = todoApi.getTodo();

        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful()) {
                    List<TodoModel> todoModelList = response.body();
                    todoAdapter.updateTodoList(todoModelList); // Update the adapter with the new list
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Toast.makeText(GetActivity.this, "Failed to load todos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
