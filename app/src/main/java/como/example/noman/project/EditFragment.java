package como.example.noman.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditFragment extends Fragment {

    EditText name, room_no, floor_no, extras;
    Button save_btn, del_btn;
    String hostel_name, hostel_room_no, hostel_floor_no, hostel_extras;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        name = view.findViewById(R.id.hostelData_hostelName);
        room_no = view.findViewById(R.id.hostelData_no_rooms);
        floor_no = view.findViewById(R.id.hostelData_no_floors);
        extras = view.findViewById(R.id.hostelData_extras);
        save_btn = view.findViewById(R.id.SaveButton);
        del_btn = view.findViewById(R.id.Delete);



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_hostel();
            }
        });

        return view;
    }

    public void delete_hostel(){
        switch_page();
    }

    public void switch_page()
    {
        Intent homePage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(homePage);
    }

    public void save()
    {
        hostel_name = name.getText().toString().trim();
        hostel_room_no = room_no.getText().toString().trim();
        hostel_floor_no = floor_no.getText().toString().trim();
        hostel_extras = extras.getText().toString().trim();

        // Create new fragment and transaction
//        Fragment newFragment = new HostelDataFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack if needed
//        transaction.replace(R.id.fragment_add_hostel, newFragment);
//        transaction.addToBackStack(null);
//
//// Commit the transaction
//        transaction.commit();
    }
}