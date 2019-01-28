package como.example.noman.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isLoggedIn;
    static WebService server; // Global server variable should be used everywhere

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        server = WebService.getInstance(this);
        //server.initialize_server();                                   //initializing the server
        /*server.getAllHostels(new WebService.Callback<WebService.HostelObjectList>() {   //getting hostel data
            @Override
            public void callbackFunction(WebService.HostelObjectList result) {
                if (result.hostelsStored.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Hostels", Toast.LENGTH_LONG).show();
            }
        });*/

        /*server.verifyUser("test@test.com", "1234", new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunction(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Verified", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Not Verified", Toast.LENGTH_LONG).show();
            }
        });*/

        /*server.getUserData("test@test.com", "1234", new WebService.Callback<WebService.userObject>() {
            @Override
            public void callbackFunction(WebService.userObject result) {
                if (result.userName != null)
                    Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
            }
        });*/

        //Creating an initial Database of Hostels if it doesn't exist

        //SharedPreferences hostelPref = getSharedPreferences("hostelInfo", MODE_PRIVATE);
        //String h = hostelPref.getString("hostels", null);
        if (false)  //set this to true to add test data to the server
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////////////////////////////////

        //check if user is logged in or not
        SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
        if (mpef.getString("logged_in", null) == null)
            isLoggedIn = false;
        else
            isLoggedIn  = true;
        //////////////////////////////////////////////////////////////

        //checking if a user logged in to his/her account
        Menu menu = navigationView.getMenu();
        if(!isLoggedIn)
        {
            ((MenuItem) menu.findItem(R.id.nav_logout)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_addhostel)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_profile)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_managehostel)).setVisible(false);
        }
        else
        {
            ((MenuItem) menu.findItem(R.id.nav_login)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_signup)).setVisible(false);
        }


        Fragment fragment = new HomeFragment();
        //Fragment fragment = new AddHostel();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        } else if (id == R.id.nav_signup) {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
        } else if (id == R.id.nav_addhostel)
        {
            Fragment fragment = new AddHostel();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_managehostel) {
            //manage hostel page goes here
        } else if (id == R.id.nav_profile) {
            //profile hostel page goes here
        } else if (id == R.id.nav_logout) {
            SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
            mpef.edit().putString("logged_in", null).apply();
            isLoggedIn = false;
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
