package com.kutubuddin.logic_todo_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kutubuddin.logic_todo_app.api.TodoApi;
import com.kutubuddin.logic_todo_app.apiClient.ApiClient;
import com.kutubuddin.logic_todo_app.model.TodoModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, date;
    private Spinner spinnerTodotype;
    private RadioGroup radioGroupPriority;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerTodotype = findViewById(R.id.spinnerTodotype);
        date = findViewById(R.id.date);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up the spinner (dropdown) with values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.todo_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTodotype.setAdapter(adapter);

        // Set date picker on EditText click
        date.setOnClickListener(v -> showDatePickerDialog());

        // Handle submit button click
        btnSubmit.setOnClickListener(v -> submitTodo());
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Format date string
            String formattedDate = selectedYear + "-" + String.format("%02d", selectedMonth + 1) + "-" + String.format("%02d", selectedDay);
            date.setText(formattedDate);
        }, year, month, day);

        // Show the dialog
        datePickerDialog.show();
    }

    private void submitTodo() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String selectedTodotype = spinnerTodotype.getSelectedItem().toString();

        // Validate Todo type
        if (selectedTodotype.equals("Select Todo Type")) {
            showToast("Please select a valid Todo type");
            return;
        }

        // Validate priority selection
        int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();
        String selectedPriority = "";
        if (selectedPriorityId != -1) {
            RadioButton selectedPriorityButton = findViewById(selectedPriorityId);
            selectedPriority = selectedPriorityButton.getText().toString();
        } else {
            showToast("Please select a priority");
            return;
        }

        // Ensure title and description are filled
        if (title.isEmpty() || description.isEmpty()) {
            showToast("Title and Description are required");
            return;
        }

        // Validate date
        String selectedDate = date.getText().toString();
        if (selectedDate.isEmpty()) {
            showToast("Please select a date");
            return;
        }

        // Create TodoModel object
        TodoModel todo = new TodoModel(0, title, description, selectedDate, selectedTodotype, selectedPriority);

        // Call API to submit the todo
        TodoApi api = ApiClient.getRetrofit().create(TodoApi.class);
        Call<Void> call = api.createTodo(todo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Todo Created Successfully");
                    navigateToGetActivity();
                } else {
                    showToast("Failed to create Todo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(PostActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToGetActivity() {
        Intent intent = new Intent(PostActivity.this, GetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
