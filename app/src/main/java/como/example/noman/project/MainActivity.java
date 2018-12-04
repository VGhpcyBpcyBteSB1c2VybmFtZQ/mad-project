package como.example.noman.project;

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
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserName = (EditText)findViewById(R.id.user_name);
        Email = (EditText)findViewById(R.id.email_id);
        Password = (EditText)findViewById(R.id.password);
        ConfirmPassword = (EditText)findViewById(R.id.confirm_password);


        UserName.setText("");
        Email.setText("");
        Password.setText("");
        ConfirmPassword.setText("");
    }
    public void SaveData(View view)
    {

        if(UserName.getText().length()==0)
        {
            UserName.setError("Field cannot be left blank.");
        }

        if(Email.getText().length()==0)
        {
            Email.setError("Field cannot be left blank.");
        }

        if(Password.getText().length()==0)
        {
            Password.setError("Field cannot be left blank.");
        }

        if(ConfirmPassword.getText().length()==0)
        {
            ConfirmPassword.setError("Confirm Password!.");
        }
        String Name = UserName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        Person myObject = new Person(Name,email,password);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString("size", String.valueOf(count));
        prefsEditor.putString("Person"+count, json);
        count++;

        prefsEditor.commit();
    }

    public void display(View view)
    {
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String mySize = mPrefs.getString("size", "");
        String json = mPrefs.getString("Person2", "");

        count = Integer.parseInt(mySize);
        Person obj = gson.fromJson(json, Person.class);



       // Toast.makeText(this, obj.getUserName(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, obj.getPassword(), Toast.LENGTH_LONG).show();



        // Toast.makeText(this, obj.getPassword(), Toast.LENGTH_LONG).show();




        //Toast.makeText(this, obj.getUserName(), Toast.LENGTH_LONG).show();

      /*  Map<String,?> keys = mPrefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("MyObject",entry.getKey() + ": " +
                    entry.getValue().toString());
        }*/





      /*  for (Object key : jsonObj.keySet()) {
            //based on you key types
            String keyStr = (String) key;
            Object keyvalue = jsonObj.get(keyStr);

            //Print key and value
            System.out.println("key: " + keyStr + " value: " + keyvalue);
        }*/

        }
}
