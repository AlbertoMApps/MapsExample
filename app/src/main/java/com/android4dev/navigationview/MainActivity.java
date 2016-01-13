package com.android4dev.navigationview;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android4dev.navigationview.Constants.Constant;
import com.android4dev.navigationview.Fragments.ContentFragment;
import com.android4dev.navigationview.api.apiPlaces;
import com.android4dev.navigationview.model.PlacesModel;
import com.google.android.gms.maps.GoogleMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity  {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RestAdapter restAdapt;
   // private PlacesModel listPlaces;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Small example of savedInstance
        if( savedInstanceState != null ) {
            Toast.makeText(this, savedInstanceState .getString("message"), Toast.LENGTH_LONG).show();
        }

        //restAdapter
        restAdapt = new RestAdapter.Builder()
                .setEndpoint(Constant.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        final apiPlaces iPlaces = restAdapt.create(apiPlaces.class);


        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.food:
                        //iPlaces
                        iPlaces.getPlaces(new Callback<PlacesModel>() {
                            @Override
                            public void success(PlacesModel placesModel, Response response) {
                                //Log.i("", "placesModel:" + placesModel.getResults().get(0).getId());
                                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                                //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                                //Fragment loading
                                Toast.makeText(getApplicationContext(), "Maps Selected", Toast.LENGTH_SHORT).show();
//                                // Add a marker in the mainActivity 1st option I made, now the layouts are changed
//                                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//                                for( int i =0; i<placesModel.getResults().size();i++ ) {
//                                    double lat = placesModel.getResults().get(i).getGeometry().getLocation().getLat();
//                                    double lng = placesModel.getResults().get(i).getGeometry().getLocation().getLng();
//                                    final LatLng place = new LatLng(lat, lng);
//                                    Marker placeMarker = mMap.addMarker(new MarkerOptions()
//                                            .position(place).title(placesModel.getResults().get(i).getName()));
//                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,12.5f));
//                                }
                                //setListPlaces(placesModel);
//                                ContentFragment fragment = new ContentFragment();
                                //send the whole list to the fragment to show some properties for the map
//                                fragment.setListPlaces(placesModel);
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame, ContentFragment.newInstance(placesModel));
                                fragmentTransaction.commit();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.checkbox:
                        Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "loading");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
