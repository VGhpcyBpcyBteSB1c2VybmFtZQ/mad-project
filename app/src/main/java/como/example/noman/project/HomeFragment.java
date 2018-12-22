package como.example.noman.project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class HomeFragment extends Fragment {

    ListView mylist;    //listView Object

    ////////////// All data related to Hostels //////////////////
    String[] hostelNames = {"Paradise Hostel", "Premium Alcazaba Hostel", "El Machico Hostel", "Einstein Hostel"};
    String[] hostelAddress = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
    String[] hostelRatings = {"4.2", "3.5", "2.3", "3.3"};
    Integer[] hostelImagesId = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
    ////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /////////////////// setting adapter here ////////////////////
        mylist = view.findViewById(R.id.listview_my_custom_listview);
        CustomListView clv = new CustomListView(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelImagesId);
        mylist.setAdapter(clv);
        /////////////////////////////////////////////////////////////

        return view;
    }


}
