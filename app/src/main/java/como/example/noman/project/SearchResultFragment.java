package como.example.noman.project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultFragment extends Fragment {

    ////////////// Getting Data of Hostels //////////////////
    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;
    private int[] hostelIDs;
    ////////////////////////////////////////////////////////////

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_result, container, false);

        final Spinner mySpinner = view.findViewById(R.id.spinner_find_hostel_fragment);
        Button btn = view.findViewById(R.id.btn_find);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mySpinner.getSelectedItem().toString();
                findByCity(city);
            }
        });




        return view;
    }

    public void findByCity(String city)
    {
        final RecyclerView listView = (RecyclerView) view.findViewById(R.id.fragment_find_hostel_recycler_view);

        WebService.getInstance(getActivity()).getHostelsByCity(city, new WebService.Callback<WebService.HostelObjectList>() {
            @Override
            public void callbackFunctionSuccess(WebService.HostelObjectList hl) {

                hostelNames = new String[hl.hostelsStored.size()];
                hostelAddress = new String[hl.hostelsStored.size()];
                hostelRatings = new String[hl.hostelsStored.size()];
                hostelCity = new String[hl.hostelsStored.size()];
                hostelRooms = new Integer[hl.hostelsStored.size()];
                hostelFloors = new Integer[hl.hostelsStored.size()];
                hostelExtras = new String[hl.hostelsStored.size()];
                hostelOwnerMail = new String[hl.hostelsStored.size()];
                hostelIDs = new int[hl.hostelsStored.size()];

                for (int i = 0; i < hl.hostelsStored.size(); i++) {
                    hostelNames[i] = hl.hostelsStored.get(i).hostelName;
                    hostelAddress[i] = hl.hostelsStored.get(i).hostelAddress;
                    hostelRatings[i] = Float.toString(hl.hostelsStored.get(i).rating);
                    hostelCity[i] = hl.hostelsStored.get(i).hostelCity;
                    hostelRooms[i] = hl.hostelsStored.get(i).no_rooms;
                    hostelFloors[i] = hl.hostelsStored.get(i).no_floors;
                    hostelExtras[i] = hl.hostelsStored.get(i).hostelExtras;
                    hostelOwnerMail[i] = hl.hostelsStored.get(i).owner_email;
                    hostelIDs[i] = hl.hostelsStored.get(i).hostel_id;

                    ///////////////////////////////////////////////
                }

                /////////////////// setting adapter here ////////////////////
                CustomRecyclerViewForEditHostel adapter = new CustomRecyclerViewForEditHostel(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelCity, hostelRooms, hostelFloors, hostelExtras, hostelOwnerMail, hostelIDs);
                adapter.isEditable = false;
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);
                listView.setLayoutManager(horizontalLayoutManager);
                listView.setAdapter(adapter);
                /////////////////////////////////////////////////////////////
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });
    }

}