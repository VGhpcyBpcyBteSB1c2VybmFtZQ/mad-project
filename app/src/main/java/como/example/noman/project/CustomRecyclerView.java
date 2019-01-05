package como.example.noman.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomRecyclerView extends  RecyclerView.Adapter<CustomRecyclerView.MyViewHolder> {

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
    private Bitmap[] hostelBitmaps;

    public CustomRecyclerView(Activity context, String[] hostelNames, String[] hostelAddress, String[] hostelRatings, String[] hostelCity, Integer[] hostelRooms, Integer[] hostelFloors, String[] hostelExtras, String[] hostelOwnerMail, Integer[] hostelImages, Bitmap[] hostelBitmaps) {

        this.context = context;
        this.hostelNames = hostelNames;
        this.hostelAddress = hostelAddress;
        this.hostelRatings = hostelRatings;
        this.hostelImages = hostelImages;
        this.hostelCity = hostelCity;
        this.hostelRooms = hostelRooms;
        this.hostelFloors = hostelFloors;
        this.hostelExtras = hostelExtras;
        this.hostelOwnerMail = hostelOwnerMail;
        this.hostelBitmaps = hostelBitmaps;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);

        // Create and return our customRecycler View Holder object.
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(hostelNames[position]);
        holder.rating.setText(hostelRatings[position]);
        holder.image.setImageBitmap(hostelBitmaps[position]);
    }

    @Override
    public int getItemCount() {
        return hostelNames.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name = null;
        public TextView rating = null;
        public ImageView image = null;

        public MyViewHolder(View view) {
            super(view);

            if(view != null)
            {
                name = (TextView) view.findViewById(R.id.item_name);
                rating = (TextView) view.findViewById(R.id.item_rating);
                image = (ImageView) view.findViewById(R.id.item_image);
            }
        }

    }
}