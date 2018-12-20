package como.example.noman.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText UserName;
    EditText Email;
    EditText Password;
    EditText ConfirmPassword;
    Button CreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserName = (EditText) findViewById(R.id.user_name);
        Email = (EditText) findViewById(R.id.email_id);
        Password = (EditText) findViewById(R.id.password);
        ConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        CreateAccount = (Button) findViewById(R.id.create_account);

        UserName.setText("");
        Email.setText("");
        Password.setText("");
        ConfirmPassword.setText("");

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserName.getText().length() == 0) {
                    UserName.setError("Field cannot be left blank.");
                    return;
                }

                if (Email.getText().length() == 0) {
                    Email.setError("Field cannot be left blank.");
                    return;
                }

                if (Password.getText().length() == 0) {
                    Password.setError("Field cannot be left blank.");
                    return;
                }

                if (ConfirmPassword.getText().length() == 0) {
                    ConfirmPassword.setError("Confirm Password!.");
                    return;
                }

                SaveData(v);

                //goto homepage after this
            }
        });
    }

    public void SaveData(View view) {

        String Name = UserName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        Person myObject = new Person(Name, email, password);  //creating new person object
        SharedPreferences mPrefs = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(myObject);
        prefsEditor.putString(email, json);

        prefsEditor.apply();
    }
}

