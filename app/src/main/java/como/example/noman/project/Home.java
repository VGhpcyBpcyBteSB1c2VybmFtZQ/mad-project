package como.example.noman.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebService server = new WebService(this);
        server.initialize_server();                                   //initializing the server
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
        if (false)
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
                Bitmap bm = BitmapFactory.decodeResource(getResources(), hostelImagesId[i]);
                bm.compress(Bitmap.CompressFormat.JPEG, 50, os);
                byte[] bObj = os.toByteArray();
                WebService.HostelObject hClass = new WebService.HostelObject(hostelNames[i], hostelAddress[i], hostelCity[i], hostelExtras[i], hostelRooms[i], hostelFloors[i], hostelOwnerMail[i], hostelRatings[i], bObj);

                server.addHostel(hClass, new WebService.Callback<Boolean>() {
                    @Override
                    public void callbackFunction(Boolean result) {
                        if (result)
                            Toast.makeText(getApplicationContext(), "Successfully Uploaded Hostel", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Failed to Uploaded Hostel", Toast.LENGTH_LONG).show();
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

        ///////////////////////// Add Product Button ////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide(); //hiding it temporarily

        fab.setOnClickListener(new ClickListener(this, getApplicationContext()));

        if(isLoggedIn)  //checking if user logged_in or not
            fab.show();

        ////////////////////////////////////////////////////////////////////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.GONE);    //hiding nav temporarily
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStackImmediate();
        }
        if (isLoggedIn)
        {
            ((FloatingActionButton)findViewById(R.id.fab)).show();
        }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
            mpef.edit().putString("logged_in", null).apply();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Home.class);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyClass
    {
        String mName, mSem;
    }
}
