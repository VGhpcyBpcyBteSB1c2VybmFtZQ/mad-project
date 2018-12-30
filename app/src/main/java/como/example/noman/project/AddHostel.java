package como.example.noman.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class AddHostel extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_hostel, container, false);

//        v.findViewById(R.id.addHostel_choose_gal).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                        //permission not granted, request it
//                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, 1001);
//                    } else {
//                        //permission already granted
//                        pickImagesFromGallery();
//                    }
//                } else {
//                    //android version is less then marshmallow
//                    pickImagesFromGallery();
//                }
//            }
//        });

        v.findViewById(R.id.addHostel_save).setOnClickListener(new ClickListener(getActivity(), getContext()));
        return v;
    }

//    private void pickImagesFromGallery() {
//        Intent i = new Intent(Intent.ACTION_PICK);
//        i.setType("image/*");
//        startActivityForResult(i, 1000);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (requestCode) {
//            case 1001:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, proceed to the normal flow.
//                    pickImagesFromGallery();
//                } else {
//                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }
}
