package como.example.noman.project;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class HomeFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final LayoutInflater inflaterForInner = inflater;

        /////////////// Getting Data for Hotels //////////////////////////////

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
                    ViewGroup parent = (ViewGroup) view.findViewById(R.id.home_fragment_table);
                    LinearLayout v = (LinearLayout) inflaterForInner.inflate(R.layout.table_row_home_category, parent);
                    v.getChildAt(i).setId(View.generateViewId());

                    TextView textView = v.getChildAt(i).findViewById(R.id.list_heading);
                    RecyclerView listView = (RecyclerView) view.findViewById(R.id.fragment_home_recycler_view);

                    //randomly setting ids to views
                    int id1 = View.generateViewId();
                    int id2 = View.generateViewId();

                    textView.setId(id1);
                    listView.setId(id2);

                    listView = (RecyclerView) view.findViewById(id2);

                    //Change heading text here
                    textView.setText("Heading " + i);

                    /////////////////// setting adapter here ////////////////////
                    CustomRecyclerView adapter = new CustomRecyclerView(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelCity, hostelRooms, hostelFloors, hostelExtras, hostelOwnerMail, hostelBitmaps);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                    listView.setLayoutManager(horizontalLayoutManager);
                    listView.setAdapter(adapter);
                    /////////////////////////////////////////////////////////////
                }
            }
        });

        return view;
    }
}