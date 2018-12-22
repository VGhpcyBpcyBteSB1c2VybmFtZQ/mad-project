package como.example.noman.project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class Signup extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText UserName = (EditText) findViewById(R.id.user_name);
        EditText Email = (EditText) findViewById(R.id.email_id);
        EditText Password = (EditText) findViewById(R.id.password);
        EditText ConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        UserName.setText("");
        Email.setText("");
        Password.setText("");
        ConfirmPassword.setText("");

        findViewById(R.id.txt_already).setOnClickListener(new ClickListener(this, getApplicationContext()));
        findViewById(R.id.create_account).setOnClickListener(new ClickListener(this, getApplicationContext()));
    }
}

