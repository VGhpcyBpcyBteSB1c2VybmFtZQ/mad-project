package como.example.noman.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListView extends ArrayAdapter<String> {

    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private Activity context;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;
    private Bitmap[] hostelBitmaps;

    CustomListView(Activity context, String[] hostelNames, String[] hostelAddress, String[] hostelRatings, String[] hostelCity, Integer[] hostelRooms, Integer[] hostelFloors, String[] hostelExtras, String[] hostelOwnerMail, Bitmap[] hostelBitmaps) {
        super(context, R.layout.listview_item, hostelNames);

        this.context = context;
        this.hostelNames = hostelNames;
        this.hostelAddress = hostelAddress;
        this.hostelRatings = hostelRatings;
        this.hostelCity = hostelCity;
        this.hostelRooms = hostelRooms;
        this.hostelFloors = hostelFloors;
        this.hostelExtras = hostelExtras;
        this.hostelOwnerMail = hostelOwnerMail;
        this.hostelBitmaps = hostelBitmaps;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.listview_item, null, false);

        ClickListener cl = new ClickListener(context, context.getApplicationContext());
        // setting parameters for click listener //
        cl.hostelName = hostelNames[position];
        cl.hostelAddress = hostelAddress[position];
        cl.hostelExtras = hostelExtras[position];
        cl.no_rooms = hostelRooms[position];
        cl.no_floors = hostelFloors[position];
        cl.owner_email = hostelOwnerMail[position];
        cl.image_bitmap = hostelBitmaps[position];
        /////////////////////////////////////////

        view.findViewById(R.id.item_readmore).setOnClickListener(cl);  //setting onclick listener to read more textview
        TextView name = (TextView) view.findViewById(R.id.item_hname);
        TextView address = (TextView) view.findViewById(R.id.item_address);
        TextView rating = (TextView) view.findViewById(R.id.item_rating);
        ImageView image = (ImageView) view.findViewById(R.id.item_image);

        //setting data to textviews
        name.setText(hostelNames[position]);
        address.setText(hostelAddress[position]);
        rating.setText(hostelRatings[position]);
        image.setImageBitmap(hostelBitmaps[position]);

        return view;
    }

}
