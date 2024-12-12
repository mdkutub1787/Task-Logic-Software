package com.kutubuddin.logic_todo_app.api;



import com.kutubuddin.logic_todo_app.model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface GetApi {

    @GET("todoapps")
    Call<List<TodoModel>> getTodo();

}
