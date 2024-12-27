package com.kutubuddin.logic_todo_app.api;

import com.kutubuddin.logic_todo_app.model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoApi {

    // GET request to view all Todos
    @GET("todoapps")
    Call<List<TodoModel>> getTodo();

    // POST request to create a new Todo
    @POST("todoapps")
    Call<Void> createTodo(@Body TodoModel todo);

    // DELETE request to delete a Todo by ID
    @DELETE("todoapps/{id}")
    Call<Void> deleteTodo(@Path("id") int id);

    // PUT request to update a Todo by ID
    @PUT("todoapps/{id}")
    Call<Void> updateTodo(@Path("id") int id, @Body TodoModel todo);

    @GET("todoapps/{id}")
    Call<TodoModel> getTodoById(@Path("id") int id);


}
