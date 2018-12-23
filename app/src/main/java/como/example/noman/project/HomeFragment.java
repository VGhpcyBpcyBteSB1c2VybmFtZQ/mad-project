package como.example.noman.project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;


public class HomeFragment extends Fragment {

    ListView mylist;    //listView Object

    ////////////// Getting Data of Hostels //////////////////
    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private Integer[] hostelImages;
    private Activity context;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;

    ////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /////////////// Getting Data for Hotels //////////////////////////////

        SharedPreferences hPref = getActivity().getSharedPreferences("hostelInfo", Context.MODE_PRIVATE);
        String json = hPref.getString("hostels", null);
        HostelDataList hl = (new Gson()).fromJson(json, HostelDataList.class);
        hostelNames = new String[hl.hostelsStored.size()];
        hostelAddress = new String[hl.hostelsStored.size()];
        hostelRatings = new String[hl.hostelsStored.size()];
        hostelImages = new Integer[hl.hostelsStored.size()];
        hostelCity = new String[hl.hostelsStored.size()];
        hostelRooms = new Integer[hl.hostelsStored.size()];
        hostelFloors = new Integer[hl.hostelsStored.size()];
        hostelExtras = new String[hl.hostelsStored.size()];
        hostelOwnerMail = new String[hl.hostelsStored.size()];

        for (int i = 0; i < hl.hostelsStored.size(); i++)
        {
            hostelNames[i] = hl.hostelsStored.get(i).hostelName;
            hostelAddress[i] = hl.hostelsStored.get(i).hostelAddress;
            hostelRatings[i] = hl.hostelsStored.get(i).rating;
            hostelImages[i] = hl.hostelsStored.get(i).image_source;
            hostelCity[i] = hl.hostelsStored.get(i).hostelCity;
            hostelRooms[i] = hl.hostelsStored.get(i).no_rooms;
            hostelFloors[i] = hl.hostelsStored.get(i).no_floors;
            hostelExtras[i] = hl.hostelsStored.get(i).hostelExtras;
            hostelOwnerMail[i] = hl.hostelsStored.get(i).owner_email;
        }

        /////////////////// setting adapter here ////////////////////
        mylist = view.findViewById(R.id.listview_my_custom_listview);
        CustomListView clv = new CustomListView(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelCity, hostelRooms, hostelFloors, hostelExtras, hostelOwnerMail, hostelImages);
        mylist.setAdapter(clv);
        /////////////////////////////////////////////////////////////

        return view;
    }


}
