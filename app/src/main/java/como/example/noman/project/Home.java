package como.example.noman.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creating an initial Database of Hostels if it doesn't exist

        SharedPreferences hostelPref = getSharedPreferences("hostelInfo", MODE_PRIVATE);
        String h = hostelPref.getString("hostels", null);
        if (h == null)
        {
            String[] hostelNames = {"Paradise Hostel", "Premium Alcazaba Hostel", "El Machico Hostel", "Einstein Hostel"};
            String[] hostelAddress = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            String[] hostelCity = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            String[] hostelRatings = {"4.2", "3.5", "2.3", "3.3"};
            Integer[] hostelImagesId = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
            Integer[] hostelRooms = {20, 10, 16, 18};
            Integer[] hostelFloors = {6, 4, 3, 3};
            String[] hostelExtras = {"AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator"};
            String[] hostelOwnerMail = {"test1@test.com", "test2@test.com", "test3@test.com", "test4@test.com"};
            int[] hostelIds = {0, 1, 2, 3};

            HostelDataList hostelList = new HostelDataList();
            for (int i = 0; i < 4; i++)
            {
                HostelDataClass hClass = new HostelDataClass(hostelNames[i], hostelAddress[i], hostelCity[i], hostelExtras[i], hostelRooms[i], hostelFloors[i], hostelOwnerMail[i], hostelImagesId[i], hostelRatings[i], hostelIds[i]);
                hostelList.hostelsStored.add(hClass);
            }

            String hListJson = (new Gson()).toJson(hostelList);
            hostelPref.edit().putString("hostels", hListJson).putInt("lastKey", 3).apply();
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
}
