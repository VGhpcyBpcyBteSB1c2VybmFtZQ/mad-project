package como.example.noman.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ManageHostelFragment extends Fragment {

    ////////////// Getting Data of Hostels //////////////////
    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;
    private Bitmap[] hostelBitmaps;
    ////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_manage_hostel, container, false);
        final RecyclerView listView = (RecyclerView) view.findViewById(R.id.fragment_manage_hostel_recycler_view);

        WebService.getInstance(getActivity()).getAllHostels(new WebService.Callback<WebService.HostelObjectList>() {
            @Override
            public void callbackFunction(WebService.HostelObjectList hl) {

                Toast.makeText(getActivity(), "Page Loaded", Toast.LENGTH_LONG).show();

                hostelNames = new String[hl.hostelsStored.size()];
                hostelAddress = new String[hl.hostelsStored.size()];
                hostelRatings = new String[hl.hostelsStored.size()];
                hostelCity = new String[hl.hostelsStored.size()];
                hostelRooms = new Integer[hl.hostelsStored.size()];
                hostelFloors = new Integer[hl.hostelsStored.size()];
                hostelExtras = new String[hl.hostelsStored.size()];
                hostelOwnerMail = new String[hl.hostelsStored.size()];
                hostelBitmaps = new Bitmap[hl.hostelsStored.size()];

                for (int i = 0; i < hl.hostelsStored.size(); i++) {
                    hostelNames[i] = hl.hostelsStored.get(i).hostelName;
                    hostelAddress[i] = hl.hostelsStored.get(i).hostelAddress;
                    hostelRatings[i] = Float.toString(hl.hostelsStored.get(i).rating);
                    hostelCity[i] = hl.hostelsStored.get(i).hostelCity;
                    hostelRooms[i] = hl.hostelsStored.get(i).no_rooms;
                    hostelFloors[i] = hl.hostelsStored.get(i).no_floors;
                    hostelExtras[i] = hl.hostelsStored.get(i).hostelExtras;
                    hostelOwnerMail[i] = hl.hostelsStored.get(i).owner_email;
                    hostelBitmaps[i] = hl.hostelsStored.get(i).getBitmap();

                    ///////////////////////////////////////////////
                }

                for (int i = 0; i < 7; i++) {

                    /////////////////// setting adapter here ////////////////////
                    CustomRecyclerView adapter = new CustomRecyclerView(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelCity, hostelRooms, hostelFloors, hostelExtras, hostelOwnerMail, hostelBitmaps);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
                    listView.setLayoutManager(horizontalLayoutManager);
                    listView.setAdapter(adapter);
                    /////////////////////////////////////////////////////////////
                }
            }
        });
        return view;
    }

}
