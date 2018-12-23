package como.example.noman.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import static android.content.Context.MODE_PRIVATE;

public class ClickListener implements View.OnClickListener {

    private Activity activity;  //use this activity or context when needed
    private Context context;

    ClickListener(Activity _activity, Context _context) //first constructor
    {
        activity = _activity;
        context = _context;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())              // add click function in this switch against the view id
        {
            case R.id.txt_already:
                goToLogin();
                break;
            case R.id.create_account:
                createAccount();
                break;
            case R.id.NoAccount:
                goToRegister();
                break;
            case R.id.signinButton:
                signin();
                break;
            case R.id.item_readmore:
                readMore();
                break;
            default:
                break;
        }
    }

    //// Implement Click Functions Here ////

    private void goToLogin() {
        Intent intent = new Intent(activity, Login.class);
        activity.startActivity(intent);
    }

    private void goToRegister() {
        Intent register = new Intent(activity, Signup.class);
        activity.startActivity(register);
    }

    private void createAccount() {
        EditText UserName = (EditText) activity.findViewById(R.id.user_name);
        EditText Email = (EditText) activity.findViewById(R.id.email_id);
        EditText Password = (EditText) activity.findViewById(R.id.password);
        EditText ConfirmPassword = (EditText) activity.findViewById(R.id.confirm_password);

        if (UserName.getText().toString().trim().length() == 0) {
            UserName.setError("Field cannot be left blank.");
            return;
        }

        if (Email.getText().toString().trim().length() == 0) {
            Email.setError("Field cannot be left blank.");
            return;
        }

        if (Password.getText().length() == 0) {
            Password.setError("Field cannot be left blank.");
            return;
        }

        if (!ConfirmPassword.getText().toString().equals(Password.getText().toString())) {
            ConfirmPassword.setError("Passwords don't match");
            return;
        }

        if(!SaveData(UserName, Email, Password))
        {
            Email.setError("There is already an account associated with this email.");
        }
        else
        {
            Toast.makeText(activity, "Account Created!", Toast.LENGTH_SHORT).show();
            Intent homePage = new Intent(activity, Home.class);
            activity.startActivity(homePage);
        }
    }

    private boolean SaveData(EditText UserName, EditText Email, EditText Password) {
        String email = Email.getText().toString().trim();
        SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
        String mail = mPrefs.getString(email, "");
        if (mail != null && !mail.equals(""))
        {
            return false;
        }

        String Name = UserName.getText().toString();
        String password = Password.getText().toString();


        Person myObject = new Person(Name, email, password);  //creating new person object

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(myObject);
        prefsEditor.putString(email, json);

        prefsEditor.apply();

        mPrefs.edit().putString("logged_in", json).apply();  //add entry in database

        return true;
    }

    private void signin() {
        EditText emailAddress = activity.findViewById(R.id.emailText);
        EditText passwordText = activity.findViewById(R.id.passText);

        String mail = emailAddress.getText().toString().trim();
        String p = passwordText.getText().toString();

        if (mail.isEmpty())
            emailAddress.setError("Enter your email!");
        else if (p.isEmpty())
            passwordText.setError("Enter your password!");

        else
        {
            SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString(mail, "none");
            if(json != null && json.equals("none")) {
                emailAddress.setError("Incorrect email!");
                return;
            }
            if(json != null && !json.isEmpty()) {
                Person obj = gson.fromJson(json, Person.class);
                String password = obj.getPassword();

                if (password.equals(p))   //Login if credentials are correct
                {
                    mPrefs.edit().putString("logged_in", json).apply();  //add entry in database
                    Intent homePage = new Intent(activity, Home.class);
                    activity.startActivity(homePage);
                }
                else
                {
                    passwordText.setError("Incorrect Password!");
                }
            }

        }

    }

    //parameters for the function that follows
    public int imageSource;
    public String hostelName;
    public String hostelAddress;

    private void readMore(){
        HostelDataFragment newFragment = new HostelDataFragment();
        newFragment.hostelAddress = hostelAddress;
        newFragment.hostelName = hostelName;
        newFragment.imageSource = imageSource;
        FragmentManager fm = ((FragmentActivity)activity).getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }
}
