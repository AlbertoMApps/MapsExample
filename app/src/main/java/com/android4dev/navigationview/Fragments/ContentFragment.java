package com.android4dev.navigationview.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android4dev.navigationview.PicassoMarker.PicassoMarker;
import com.android4dev.navigationview.R;
import com.android4dev.navigationview.model.PlacesModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 04-06-2015.
 */
public class ContentFragment extends Fragment implements OnMapReadyCallback {

    private PlacesModel listPlaces;
    private GoogleMap mMap;
    private PicassoMarker pm;

    /**
     *A fragment new instance partner
     *
     */
    public static ContentFragment newInstance(PlacesModel placesModel){
        Bundle b = new Bundle();
        b.putParcelable("listPlaces", placesModel);
        ContentFragment f= new ContentFragment();
        f.setArguments(b);
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listPlaces = getArguments().getParcelable("listPlaces");
        View v = inflater.inflate(R.layout.activity_maps,container,false);
       // TextView textView = (TextView) v.findViewById(R.id.textView);
       // textView.setText(listPlaces.getResults().get(0).getName());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

//onMapReady
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        for( int i =0; i<listPlaces.getResults().size();i++ ) {
            double lat = listPlaces.getResults().get(i).getGeometry().getLocation().getLat();
            double lng = listPlaces.getResults().get(i).getGeometry().getLocation().getLng();
            final LatLng place = new LatLng(lat, lng);
            MarkerOptions placeMarker = new MarkerOptions()
                    .position(place)
                    .title(listPlaces.getResults().get(i).getName())
                    .snippet("Scope: " + listPlaces.getResults().get(i).getScope()) ;//arrow icon
            //listPlaces Opening hours are null by defaultsfor this API....
//            if(listPlaces.getResults().get(i).getOpeningHours().getOpenNow()){
//                placeMarker.setSnippet(" It is open now ");
//            }
            //load icons with Picasso Marker option
            pm = new PicassoMarker( mMap.addMarker(placeMarker) );
            Picasso.with(getActivity()).load(listPlaces.getResults().get(i).getIcon()).into(pm);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,12.5f));
        }
    }

//methods
//    public void setListPlaces(PlacesModel listPlaces) {
//        this.listPlaces = listPlaces;
//    }
//
//    public PlacesModel getListModel (){
//        return listPlaces;
//    }


}
