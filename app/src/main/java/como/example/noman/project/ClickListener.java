package como.example.noman.project;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class ClickListener implements  View.OnClickListener {

    private Activity activity;  //use this activity or context when needed
    private Context context;

    ClickListener(Activity _activity, Context _context) //first constructor
    {
        activity = _activity;
        context = _context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())              // add click function in this switch against the view id
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
            case R.id.item_image:
                readMore();
                break;
            case R.id.signup_GoToHome:
            case R.id.goToHome:
                goToHome();
                break;
            case R.id.addHostel_save:
                addHostelToDatabase();
                break;

            default:
                break;
        }
    }

    //// Implement Click Functions Here ////

    private void goToAddHostel() {
        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        AddHostel newFragment = new AddHostel();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }

    private void goToLogin() {
        Intent intent = new Intent(activity, Login.class);
        activity.startActivity(intent);
    }

    private void goToHome() {
        Intent intent = new Intent(activity, HomeActivity.class);
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

        if (!SaveData(UserName, Email, Password)) {
            Email.setError("There is already an account associated with this email.");
        } else {
            Toast.makeText(activity, "Account Created!", Toast.LENGTH_SHORT).show();
            Intent homePage = new Intent(activity, HomeActivity.class);
            activity.startActivity(homePage);
        }
    }

    private boolean SaveData(EditText UserName, EditText Email, EditText Password) {
        String email = Email.getText().toString().trim();
        SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
        String mail = mPrefs.getString(email, "");
        if (mail != null && !mail.equals("")) {
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

        else {
            SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString(mail, "none");
            if (json != null && json.equals("none")) {
                emailAddress.setError("Incorrect email!");
                return;
            }
            if (json != null && !json.isEmpty()) {
                Person obj = gson.fromJson(json, Person.class);
                String password = obj.getPassword();

                if (password.equals(p))   //Login if credentials are correct
                {
                    mPrefs.edit().putString("logged_in", json).apply();  //add entry in database
                    Intent homePage = new Intent(activity, HomeActivity.class);
                    activity.startActivity(homePage);
                } else {
                    passwordText.setError("Incorrect Password!");
                }
            }

        }

    }

    //parameters for the function that follows
    public String hostelName;
    public String hostelAddress;
    public String hostelExtras;
    public int no_rooms;
    public int no_floors;
    public Bitmap image_bitmap;
    public String owner_email;

    private void readMore() {
        HostelDataFragment newFragment = new HostelDataFragment();
        newFragment.hostelAddress = hostelAddress;
        newFragment.hostelName = hostelName;
        newFragment.hostelRooms = no_rooms;
        newFragment.hostelFloors = no_floors;
        newFragment.hostelExtras = hostelExtras;
        newFragment.ownerMail = owner_email;
        newFragment.image_bitmap = image_bitmap;
        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }

    private void addHostelToDatabase() {

        EditText name = ((EditText) activity.findViewById(R.id.addHostel_name));
        EditText address = ((EditText) activity.findViewById(R.id.addHostel_address));
        EditText roomsE = ((EditText) activity.findViewById(R.id.addHostel_rooms));
        EditText floorsE = ((EditText) activity.findViewById(R.id.addHostel_floors));
        EditText extras = ((EditText) activity.findViewById(R.id.addHostel_extras));

        if (name.getText().toString().trim().length() == 0) {
            name.setError("Field Empty");
            return;
        }
        if (address.getText().toString().trim().length() == 0) {
            address.setError("Field Empty");
            return;
        }
        if (roomsE.getText().toString().trim().length() == 0) {
            roomsE.setError("Field Empty");
            return;
        }
        if (floorsE.getText().toString().trim().length() == 0) {
            floorsE.setError("Field Empty");
            return;
        }
        if (extras.getText().toString().trim().length() == 0) {
            extras.setError("Field Empty");
            return;
        }
        if (AddHostel._bitmap == null) {
            Toast.makeText(activity, "Please Select an Image", Toast.LENGTH_SHORT).show();
            return;
        }

        String hostelName_local = name.getText().toString();
        String hostelAddress_local = address.getText().toString();
        String hostelCity_local = ((Spinner) activity.findViewById(R.id.addHostel_city)).getSelectedItem().toString();
        String hostelFacilities_local = extras.getText().toString();
        int rooms = Integer.parseInt(roomsE.getText().toString());
        int floors = Integer.parseInt(floorsE.getText().toString());
        int img = -1;

        SharedPreferences mpref = activity.getSharedPreferences("info", MODE_PRIVATE);
        String personJson = mpref.getString("logged_in", null);
        Person personObj;
        HostelDataClass hostel;
        if (personJson != null) {
            personObj = (new Gson()).fromJson(personJson, Person.class);
            SharedPreferences hostelPref = activity.getSharedPreferences("hostelInfo", MODE_PRIVATE);
            int id = 1 + hostelPref.getInt("lastKey", 0);
            hostelPref.edit().putInt("lastKey", id).apply();

            /////// compressing the bitmap by a factor of 50 and storing in file//////////
            ContextWrapper cw = new ContextWrapper(context);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,Integer.toString(id)+".jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                AddHostel._bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            /////////////////////////////////////////////////////////////

            hostel = new HostelDataClass(hostelName_local, hostelAddress_local, hostelCity_local, hostelFacilities_local, rooms, floors, personObj.getEmail(), img, "0", id);
            String hostelListJson = hostelPref.getString("hostels", null);
            if (hostelListJson != null) {
                HostelDataList hostelListObj = (new Gson()).fromJson(hostelListJson, HostelDataList.class);
                hostelListObj.hostelsStored.add(hostel);
                hostelListJson = (new Gson()).toJson(hostelListObj);
                hostelPref.edit().putString("hostels", hostelListJson).apply();
            } else {
                HostelDataList hostelListObj = new HostelDataList();
                hostelListObj.hostelsStored.add(hostel);
                hostelListJson = (new Gson()).toJson(hostelListObj);
                hostelPref.edit().putString("hostels", hostelListJson).apply();
            }

        }

        Toast.makeText(activity, "Hostel Added", Toast.LENGTH_SHORT).show();
        goToHome();

    }
}