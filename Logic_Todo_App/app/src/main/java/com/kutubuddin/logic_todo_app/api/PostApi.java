package com.kutubuddin.logic_todo_app.api;



import com.kutubuddin.logic_todo_app.model.TodoModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostApi {

    // POST request to create a new Todo
    @POST("todoapps")
    Call<Void> createTodo(@Body TodoModel todo);

}
