package website.timrobinson.opencvtutorial;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.parse.ParseUser;

import java.util.List;

public class Map extends AppCompatActivity implements PermissionsListener, View.OnClickListener {
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;

    private View bg_topview;
    private View bg_botView;
    private LinearLayout logoView;
    private ImageView floatButton;
    private ImageView floatButtonProfile;


    private LinearLayout ly1,ly2,ly3,profileView;

    private LinearLayout ly_l1,ly_l2,ly_l3;
    private ImageView iv_l1,iv_l2,iv_l3;
    private TextView iv_exit;

    private  LinearLayout ly_congrats;
    private TextView countOfPlantedTrees;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibG9jaWFsIiwiYSI6ImNqcXk2NTlkczAwOTQ0OG52OWZmYWYwOWYifQ.CX4oYv51xMu9Phmw7dUvDQ.CX4oYv51xMu9Phmw7dUvDQ");
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        Boolean redirect = intent.getBooleanExtra("Redirect",false);


        if(redirect != null)
            if(redirect == true)
                showPlantTreeDialog();

        bg_topview = findViewById(R.id.topview);
        bg_botView = findViewById(R.id.botview);
        logoView = findViewById(R.id.logoView);
        floatButton = findViewById(R.id.floatButton);
        floatButtonProfile = findViewById(R.id.floatButtonProfile);
        profileView = findViewById(R.id.profileView);
        profileView.bringToFront();
        floatButtonProfile.bringToFront();
        floatButtonProfile.setOnClickListener(this);
        floatButton.setOnClickListener(this);

        bg_topview.bringToFront();
        bg_botView.bringToFront();
        logoView.bringToFront();

        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle("mapbox://styles/locial/ck09vofvt1qnu1dlj9tn6ijoq", new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        Map.this.mapboxMap = mapboxMap;
                        enableLocationComponent();
// Map is set up and the style has loaded. Now you can add data or make other map adjustments
                    }

                });

                addMarkerTrees(39.958629, 32.869810,mapboxMap);
                addMarkerTrees(39.965309, 32.878654,mapboxMap);
                addMarkerTrees(39.952613, 32.868199,mapboxMap);
                addMarkerFeeds(39.957730, 32.879162,mapboxMap);

                CameraPosition position = new CameraPosition.Builder()
                        .tilt(60)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
            }

        });
    }
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationComponent.activateLocationComponent(getApplicationContext(),mapboxMap.getStyle());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void addMarkerTrees(double lat,double lon,MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        if (markerOptions!=null)
        {
            markerOptions.title("Fidan Dikim Alan??");
            IconFactory iconFactory = IconFactory.getInstance(Map.this);
            Icon icon = iconFactory.fromResource(R.drawable.trees);
            markerOptions.icon(icon);
            markerOptions.position(new LatLng(lat, lon));
            mapboxMap.addMarker(markerOptions);}

    }


    private void addMarkerFeeds(double lat,double lon,MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        if (markerOptions!=null)
        {
            markerOptions.title("Fidan Bankas??");
            IconFactory iconFactory = IconFactory.getInstance(Map.this);
            Icon icon = iconFactory.fromResource(R.drawable.seed);
            markerOptions.icon(icon);
            markerOptions.position(new LatLng(lat, lon));
            mapboxMap.addMarker(markerOptions);}

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

        if (granted) {
            enableLocationComponent();
        } else {
            //Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.floatButton :

                //Toast.makeText(getApplicationContext(),"A??a?? dikim sayfas??n?? a??",0).show();

                showPlantTreeDialog();

                break;

            case R.id.layout_1 :


                break;

            case R.id.layout_2 :

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }

                QRIntent();

                break;

            case R.id.layout_3 :

                VerifyCameraIntent();

                break;

            case R.id.floatButtonProfile :

                showProfileDialogNoCongrats();

                break;

            case R.id.tv_exit:

                ParseUser.logOut();
                Intent intent = new Intent(this,Auth.class);
                startActivity(intent);

                break;

        }

    }

    private void showProfileDialogNoCongrats() {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_profile);

        ly_congrats = dialog.findViewById(R.id.ly_congrats);
        iv_exit = dialog.findViewById(R.id.tv_exit);

        ly_congrats.setVisibility(View.INVISIBLE);
        countOfPlantedTrees = dialog.findViewById(R.id.tv_countOfPlantedTrees);
        iv_exit.setOnClickListener(this);

        GlobalData globalData = new GlobalData();

        int countOfPlantedTreesInt = globalData.getCountOfPlantedTrees(this);
        countOfPlantedTrees.setText( " "+ countOfPlantedTreesInt + " ");

        GlobalData globalData1 = new GlobalData();

        RoundCornerProgressBar progress2 = (RoundCornerProgressBar) dialog.findViewById(R.id.progress_1);
        progress2.setProgressColor(Color.parseColor("#2DBB54"));
        progress2.setProgressBackgroundColor(Color.parseColor("#C3CDD6"));
        progress2.setMax(10);
        progress2.setProgress(globalData.getCountOfPlantedTrees(this));

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    private void VerifyCameraIntent() {

        Intent intent = new Intent(this,VerifyCamera.class);
        startActivity(intent);

    }

    private void QRIntent() {

        Intent intent = new Intent(this,QRActivity.class);
        startActivity(intent);

    }

    private void showPlantTreeDialog() {

        GlobalData globalData = new GlobalData();
        Boolean QRstate = globalData.getQRCode();
        Boolean CVerifyState = globalData.getCVerify();

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_ptree);



        ly1 = dialog.findViewById(R.id.layout_1);
        ly2 = dialog.findViewById(R.id.layout_2);
        ly3 = dialog.findViewById(R.id.layout_3);

        ly_l1 = dialog.findViewById(R.id.ly_l1);
        ly_l2 = dialog.findViewById(R.id.ly_l2);
        ly_l3 = dialog.findViewById(R.id.ly_l3);

        iv_l1 = dialog.findViewById(R.id.iv_l1);
        iv_l2 = dialog.findViewById(R.id.iv_l2);
        iv_l3 = dialog.findViewById(R.id.iv_l3);

        //CVerifyState

        if (QRstate != null){
            if (QRstate == true) {

                ly_l2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                iv_l2.setImageResource(R.drawable.greenmark);

            } else {

                ly_l2.setBackgroundColor(Color.RED);
                iv_l2.setImageResource(R.drawable.redcross);

            }

        }


        if (CVerifyState != null){
            if (CVerifyState == true) {

                ly_l3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                iv_l3.setImageResource(R.drawable.greenmark);

            } else {

                ly_l3.setBackgroundColor(Color.RED);
                iv_l3.setImageResource(R.drawable.redcross);

            }

        }


        ly1.setOnClickListener(this);
        ly2.setOnClickListener(this);
        ly3.setOnClickListener(this);

        dialog.show();

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(QRstate != null && CVerifyState != null){
            if(QRstate && CVerifyState){

                showProfileDialog();
                dialog.cancel();

                int i = globalData.getCountOfPlantedTrees(this) + 1;

                globalData.setCountOfPlantedTrees(i, this);

                QRstate = false;
                CVerifyState = false;
                globalData.setQRCode(false);
                globalData.setCVerify(false);





            }
        }



    }

    private void showProfileDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_profile);

        ly_congrats = dialog.findViewById(R.id.ly_congrats);
        countOfPlantedTrees = dialog.findViewById(R.id.tv_countOfPlantedTrees);

        GlobalData globalData = new GlobalData();

        int countOfPlantedTreesInt = globalData.getCountOfPlantedTrees(this);
        countOfPlantedTrees.setText( " "+ countOfPlantedTreesInt + " ");

        GlobalData globalData1 = new GlobalData();

        RoundCornerProgressBar progress2 = (RoundCornerProgressBar) dialog.findViewById(R.id.progress_1);
        progress2.setProgressColor(Color.parseColor("#2DBB54"));
        progress2.setProgressBackgroundColor(Color.parseColor("#C3CDD6"));
        progress2.setMax(10);
        progress2.setProgress(globalData.getCountOfPlantedTrees(this));

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }
}
