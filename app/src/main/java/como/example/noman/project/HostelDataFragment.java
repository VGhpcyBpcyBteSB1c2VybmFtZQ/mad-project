package como.example.noman.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HostelDataFragment extends Fragment {

    public String hostelName = null;
    public int hostelRooms = -1;
    public int hostelFloors = -1;
    public String hostelExtras = null;
    public String hostelAddress = null;
    public String ownerMail = null;
    public int imageSource = -1;
    public Bitmap image_bitmap = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hostel_data, container, false);

        if (hostelName != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_hostelName)).setText(hostelName);
        }
        if (hostelRooms != -1)
        {
            ((TextView)v.findViewById(R.id.hostelData_no_rooms)).setText(Integer.toString(hostelRooms));
        }
        if (hostelFloors != -1)
        {
            ((TextView)v.findViewById(R.id.hostelData_no_floors)).setText(Integer.toString(hostelFloors));
        }
        if (hostelExtras != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_extras)).setText(hostelExtras);
        }
        if (imageSource != -1)
        {
            ((ImageView)v.findViewById(R.id.hostelData_image)).setImageResource(imageSource);
        }
        else if (image_bitmap != null)
        {
            ((ImageView)v.findViewById(R.id.hostelData_image)).setImageBitmap(image_bitmap);
        }
        if (hostelAddress != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_hostelAddress)).setText(hostelAddress);
        }
        if (ownerMail != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_ownerContact)).setText(ownerMail);
        }
        return v;
    }
}
