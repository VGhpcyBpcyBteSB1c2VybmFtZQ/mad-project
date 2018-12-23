package como.example.noman.project;

import android.app.Activity;
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
    private Integer[] hostelImages;
    private Activity context;

    public CustomListView(Activity context, String[] hostelNames, String[] hostelAddress, String[] hostelRatings, Integer[] hostelImages) {
        super(context, R.layout.listview_item, hostelNames);

        this.context = context;
        this.hostelNames = hostelNames;
        this.hostelAddress = hostelAddress;
        this.hostelRatings = hostelRatings;
        this.hostelImages = hostelImages;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View holder = convertView;

        ViewHolder viewHolder = null;
        if(holder == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            holder = layoutInflater.inflate(R.layout.listview_item, null, false);
            viewHolder = new ViewHolder(holder);
            holder.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) holder.getTag();
        }
        ClickListener cl = new ClickListener(context, context.getApplicationContext());
        // setting parameters for click listener //
        cl.imageSource = hostelImages[position];
        cl.hostelName = hostelNames[position];
        cl.hostelAddress = hostelAddress[position];
        /////////////////////////////////////////
        holder.findViewById(R.id.item_readmore).setOnClickListener(cl);  //setting onclick listener
        viewHolder.name.setText(hostelNames[position]);
        viewHolder.address.setText(hostelAddress[position]);
        viewHolder.rating.setText(hostelRatings[position]);
        viewHolder.image.setImageResource(hostelImages[position]);
        return holder;
    }

    class ViewHolder
    {
        TextView name;
        TextView address;
        TextView rating;
        ImageView image;

        ViewHolder(View v)
        {
            name = (TextView) v.findViewById(R.id.item_hname);
            address = (TextView) v.findViewById(R.id.item_address);
            rating = (TextView) v.findViewById(R.id.item_rating);
            image = (ImageView) v.findViewById(R.id.item_image);
        }
    }
}
