package como.example.noman.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity {

    private boolean isLoggedIn;
    static WebService server; // Global server variable should be used everywhere

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        server = WebService.getInstance(this);
        server.initialize_server();                                   //initializing the server
        server.getAllHostels(new WebService.Callback<WebService.HostelObjectList>() {   //getting hostel data
            @Override
            public void callbackFunction(WebService.HostelObjectList result) {
                if (result.hostelsStored.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Hostels", Toast.LENGTH_LONG).show();
            }
        });

        server.verifyUser("test@test.com", "1234", new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunction(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Verified", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Not Verified", Toast.LENGTH_LONG).show();
            }
        });

        server.getUserData("test@test.com", "1234", new WebService.Callback<WebService.UserObject>() {
            @Override
            public void callbackFunction(WebService.UserObject result) {
                if (result.userName != null)
                    Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
            }
        });

        //Creating an initial Database of Hostels if it doesn't exist

        //SharedPreferences hostelPref = getSharedPreferences("hostelInfo", MODE_PRIVATE);
        //String h = hostelPref.getString("hostels", null);
        if (true)  //set this to true to add test data to the server
        {
            String[] hostelNames = {"Paradise Hostel", "Premium Alcazaba Hostel", "El Machico Hostel", "Einstein Hostel"};
            String[] hostelAddress = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            String[] hostelCity = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            float[] hostelRatings = {4.2f, 3.5f, 2.3f, 3.3f};
            Integer[] hostelImagesId = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
            Integer[] hostelRooms = {20, 10, 16, 18};
            Integer[] hostelFloors = {6, 4, 3, 3};
            String[] hostelExtras = {"AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator"};
            String[] hostelOwnerMail = {"test@test.com", "test@test.com", "test@test.com", "test@test.com"};

            for (int i = 0; i < 4; i++)
            {
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                BitmapFactory.Options boundOptions = new BitmapFactory.Options(); //options to get image data without loading bitmap into memory
                boundOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), hostelImagesId[i], boundOptions);
                int sampleSize = WebService.getSampleSize(boundOptions, 500, 500);  //get the sample factor

                BitmapFactory.Options finalOptions = new BitmapFactory.Options();  //final options to get the bitmap
                finalOptions.inSampleSize = sampleSize;
                Bitmap bm = BitmapFactory.decodeResource(getResources(), hostelImagesId[i], finalOptions);

                bm.compress(Bitmap.CompressFormat.JPEG, 50, os); //compressing the final bitmap to store in byteArrayStream
                byte[] bObj = os.toByteArray();
                WebService.HostelObject hClass = new WebService.HostelObject(hostelNames[i], hostelAddress[i], hostelCity[i], hostelExtras[i], hostelRooms[i], hostelFloors[i], hostelOwnerMail[i], hostelRatings[i], bObj);

                server.addHostel(hClass, new WebService.Callback<Boolean>() {
                    @Override
                    public void callbackFunction(Boolean result) {
                        if (result)
                            Toast.makeText(getApplicationContext(), "Successfully Uploaded Hostel", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Failed to Upload Hostel", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        /////////////////////////////////////////////////////////////


        //check if user is logged in or not
        SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
        if (mpef.getString("logged_in", null) == null)
            isLoggedIn = false;
        else
            isLoggedIn  = true;
        ///////////////////////////////////

        Fragment fragment = new HomeFragment();
        //Fragment fragment = new AddHostel();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        if (!isLoggedIn)
        {
            menu.removeItem(R.id.action_logout);
        }
        else
        {
            menu.removeItem(R.id.action_login);
            menu.removeItem(R.id.action_signup);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
            mpef.edit().putString("logged_in", null).apply();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.action_login) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.action_signup) {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
