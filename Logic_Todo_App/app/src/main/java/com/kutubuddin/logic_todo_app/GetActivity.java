package com.kutubuddin.logic_todo_app;


import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        todoList=findViewById(R.id.todoListRecyclerView);

        todoList.setLayoutManager(new LinearLayoutManager(this));

        TodoApi todoApi = ApiClient.getRetrofit().create(TodoApi.class);

        Call<List<TodoModel>> call = todoApi.getTodo();


        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {

                if (response.isSuccessful()) {
                    List<TodoModel> todoModelList = response.body();

                    // Set up RecyclerView with the adapter
                    todoAdapter = new TodoAdapter(todoModelList, getApplicationContext() );
                    todoList.setAdapter(todoAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {

            }
        });

        // FloatingActionButton click listener
        FloatingActionButton fabPost = findViewById(R.id.fabAddTodo);
        fabPost.setOnClickListener(view -> {
            // Navigate to PostActivity when FAB is clicked
            Intent intent = new Intent(GetActivity.this, PostActivity.class);
            startActivity(intent);
        });

    }

}