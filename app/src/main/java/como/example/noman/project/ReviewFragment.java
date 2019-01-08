package como.example.noman.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewFragment extends Fragment {

    RatingBar ratingBar;
    TextView comment_field;
    Button comment_button;
    String added_comment;
    LinearLayout layout;
    boolean already_rated = false;
    public static float rated =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        ratingBar = view.findViewById(R.id.ratingBar);

        comment_field = view.findViewById(R.id.commentBar);
        comment_button = view.findViewById(R.id.commentButton);

        comment_field.setVisibility(View.INVISIBLE);
        comment_button.setVisibility(View.INVISIBLE);



        layout = (LinearLayout) view.findViewById(R.id.cmt);
        rating();
        comment();

        return view;
    }

    public void addComment(String Name, int Image, float Rating, String Coment, LinearLayout parent)
    {
        View comment_field = getLayoutInflater().inflate(R.layout.comment_block, null);
        TextView user_name = comment_field.findViewById(R.id.name);
        TextView user_rating = comment_field.findViewById(R.id.user_rated);
        TextView user_comments = comment_field.findViewById(R.id.user_comment);
        ImageView user_img = comment_field.findViewById(R.id.user_img);

        user_name.setText(Name);
        user_rating.setText(Float.toString(Rating));
        user_comments.setText(Coment);
//        user_img.setImageResource(Image);


        parent.addView(comment_field);

    }

    public void comment()
    {
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                added_comment = comment_field.getText().toString().trim();

                if(!added_comment.isEmpty() &&  rated != 0 && !already_rated )
                {
                    //added to database
                    already_rated = true;
                    addComment("jon", R.drawable.login_img, rated, added_comment, layout);
//                    comment_field.setText("");
                    comment_field.setVisibility(View.INVISIBLE);
                    comment_button.setVisibility(View.INVISIBLE);
                }
                else  if (rated == 0)
                    Toast.makeText(getActivity(),"Rate it",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Enter Comment",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rating()
    {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rated = rating;
                ratingBar.setIsIndicator(true);
                ratingBar.setRating(rating);
                comment_field.setVisibility(View.VISIBLE);
                comment_button.setVisibility(View.VISIBLE);
            }
        });
    }
}