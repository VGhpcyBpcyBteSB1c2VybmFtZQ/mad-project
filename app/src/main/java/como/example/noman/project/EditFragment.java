package como.example.noman.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditFragment extends Fragment {

    EditText name, room_no, floor_no, extras, address, contact;
    Button save_btn;
    String hostel_name, hostel_room_no, hostel_floor_no, hostel_extras, hostel_address, hostel_contact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        name = view.findViewById(R.id.hostelData_hostelName);
        room_no = view.findViewById(R.id.hostelData_no_rooms);
        floor_no = view.findViewById(R.id.hostelData_no_floors);
        extras = view.findViewById(R.id.hostelData_extras);
        address = view.findViewById(R.id.hostelData_hostelAddress);
        contact = view.findViewById(R.id.hostelData_ownerContact);
        save_btn = view.findViewById(R.id.SaveButton);



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        return view;
    }

    public void save()
    {
        hostel_name = name.getText().toString().trim();
        hostel_room_no = room_no.getText().toString().trim();
        hostel_floor_no = floor_no.getText().toString().trim();
        hostel_extras = extras.getText().toString().trim();
        hostel_address = address.getText().toString().trim();
        hostel_contact = contact.getText().toString().trim();
    }
}