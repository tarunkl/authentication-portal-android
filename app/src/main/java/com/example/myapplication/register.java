package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {
    private static final String TAG ="register";
    private TextView displaydate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    TextView login,Register,date1;
    EditText email,password,fullname,phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        displaydate=(TextView) findViewById(R.id.date);
        email=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        fullname=findViewById(R.id.editTextTextPersonName);
        phonenumber=findViewById(R.id.editTextTextPersonName2);
        Register= (TextView) findViewById(R.id.textView3);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser(createRequest());
            }
        });

        displaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year =cal.get(Calendar.YEAR);
                int month =cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(register.this,
                        android.R.style.Theme,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                displaydate.setText(date);
            }
        };
        login=findViewById(R.id.logintext);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,loginpage.class));
            }

        });
        date1=(TextView) findViewById(R.id.date);
    }
    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email.getText().toString());
        userRequest.setPassword(password.getText().toString());
        userRequest.setPhone_number(phonenumber.getText().toString());
        userRequest.setFullname(fullname.getText().toString());
        userRequest.setEmail(email.getText().toString());
        userRequest.setDate_of_birth(date1.getText().toString());

        return userRequest;
    }
    public void saveUser(UserRequest userRequest){
        Call<UserResponse> userResponseCall =Apiclass.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(register.this,"Saved Successfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(register.this,"Request Failed",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(register.this,"Request Failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}