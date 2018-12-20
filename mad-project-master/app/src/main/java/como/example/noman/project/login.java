package como.example.noman.project;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class login extends Activity {

    TextView emailAddress;
    Button btn;
    TextView passwordText;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress = findViewById(R.id.emailText);
        btn = findViewById(R.id.signinButton);
        passwordText = findViewById(R.id.passText);

        registerButton = findViewById(R.id.NoAccount);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(register);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void switchActivity()
    {
        Toast.makeText(this, "switching Activity", Toast.LENGTH_LONG).show();

//        Intent homePage = new Intent(this, home.class);
//        startActivity(homePage);
    }

    public void signin() {

        String mail = emailAddress.getText().toString();
        String p = passwordText.getText().toString();

        mail = mail.trim();

        if (mail.isEmpty())
            Toast.makeText(this, "Enter your Email", Toast.LENGTH_LONG).show();
        else if (p.isEmpty())
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_LONG).show();

        else if (check(mail, p))
            switchActivity();

    }

    private boolean check(String mail, String p)
    {
        SharedPreferences mPrefs = getSharedPreferences("info", Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = mPrefs.getString(mail, "none");

        if(json.equals("none")) {
            Toast.makeText(this, "Email was not found", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!json.isEmpty()) {
            Person obj = gson.fromJson(json, Person.class);
            String password = obj.getPassword();

            if (password.equals(p))
                return true;
            else {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
            }
        }

        return false;
    }
}