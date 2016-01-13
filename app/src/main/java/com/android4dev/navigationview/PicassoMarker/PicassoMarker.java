package com.android4dev.navigationview.PicassoMarker;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by TAE_user0 on 08/01/2016.
 */
public class PicassoMarker implements Target {

    Marker mMarker;

    public PicassoMarker(Marker marker) {
        Log.d("test: ", "init marker");

        this.mMarker = marker;

    }

    @Override
    public int hashCode() {
        return mMarker.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PicassoMarker) {
            Marker marker = ((PicassoMarker) o).mMarker;
            return mMarker.equals(marker);
        } else {
            return false;
        }
    }


    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d("test: ", "bitmap loaded");
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
    }


    public void onBitmapFailed(Drawable errorDrawable) {
        Log.d("test: ", "bitmap fail");
    }


    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.d("test: ", "bitmap preload");
    }
}
