// This class acts as an interface to interact with the server
// Different services can be requested from the remote server
// Like retrieving and submitting data to the database etc.

/*
    NOTE:
        Some functions require a callback object to be passed as well in which the callbackFunction should be overridden.
        (telling it what to do once the data is retrieved from the server)
        This interface is implemented at the bottom of this class
*/

package como.example.noman.project;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebService {

    private Activity context;
    private RequestQueue queue;

    public WebService(Activity _context)   //class constructor, requires activity context
    {
        context = _context;
        queue = Volley.newRequestQueue(context);
    }

    public void initialize_server()   //should be called once in the beginning, initializes the server database if needed
    {
        String url = "http://192.168.10.6/mad-proj/init.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "Unable to connect",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    public void getAllHostels(final Callback<HostelObjectList> _callback)
    {
        String url = "http://192.168.10.6/mad-proj/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HostelObjectList result = (new Gson()).fromJson(response, HostelObjectList.class);
                _callback.callbackFunction(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Unable to connect",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_all_hostel_data", "");
                return parameter;
            }
        };

        queue.add(stringRequest);
    }

    // this is the Callback interface (abstract class) which is required by certain methods from the WebService class
    // you will override the callbackFunction which tells the WebService class what to do after the data is finally
    // fetched from the server. It takes as input the object containing retrieved data which will be different for each method

    interface Callback<T>
    {
        public void callbackFunction(T result);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // below are different types of objects that the WebService will return/take as input

    class HostelObject   //Object containing the hostel data
    {
        public String hostelName;
        public String hostelAddress;
        public String hostelCity;
        public String hostelExtras;
        public float rating;
        public int no_rooms;
        public int no_floors;
        public String owner_email;
        public int hostel_id;

        HostelObject(String _name, String _address, String _city, String _extras, int _rooms, int _floors, String _owner, float _rating, int _id) {
            hostelName = _name;
            hostelAddress = _address;
            hostelCity = _city;
            hostelExtras = _extras;
            no_rooms = _rooms;
            no_floors = _floors;
            owner_email = _owner;
            rating = _rating;
            hostel_id = _id;
        }
    }

    class HostelObjectList  //Object containing the data of multiple hostels (this one is used if multiple hostels are returned from the webservice)
    {
        public List<HostelObject> hostelsStored;

        HostelObjectList()
        {
            hostelsStored = new ArrayList<>();
        }
    }
}
