package app.moviedbd.afg.com.moviedbapp.main.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.moviedbd.afg.com.moviedbapp.domain.VolleySingleton;
import app.moviedbd.afg.com.moviedbapp.model.Movie;

/**
 * Created by Usuario on 03/01/2017.
 */

public class MainFragmentPresenter implements MainFragmentContract.Presenter {

    MainFragmentContract.View mView;
    private final String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=2bf42b27584e8eb1ffea02e97a50ddd2";
    private final String PAGINATOR_URL = "&page=";
    private final String FILTER_URL = "http://api.themoviedb.org/3/search/movie?api_key=2bf42b27584e8eb1ffea02e97a50ddd2&query=";

    private static final int ITEMS_PER_PAGE = 20;
    private int maxItems=0;

    private String filterText="";

    public MainFragmentPresenter(MainFragmentContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    public MainFragmentContract.View getView(){
        return mView;
    }

    @Override
    public void start() {
        //NOTHING TO DO HERE
    }

    @Override
    public void getMovies(Context ctx) {
        moviesRequest(ctx,1, POPULAR_URL);
    }

    @Override
    public void loadMoreData(Context ctx, int page) {
        if(TextUtils.isEmpty(filterText)){
            moviesRequest(ctx,page,POPULAR_URL+PAGINATOR_URL+page);
        } else {
            moviesRequest(ctx,page,FILTER_URL+filterText+PAGINATOR_URL+page);
        }
    }

    private void moviesRequest(Context ctx, int page, String url) {
        final boolean isScroll = (Integer.compare(page,1) > 0);
        if(!isScroll){
            getView().onInitLoading();

        } else {
            getView().initPaginationLoading();
        }
        VolleySingleton volley = VolleySingleton.getInstance(ctx);
        final Gson myGson = new Gson();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arrayResponse= response.getJSONArray("results");
                            maxItems = Integer.parseInt(response.get("total_results").toString());
                            if(arrayResponse.length()>0){
                                for(int i=0; i<arrayResponse.length(); i++){
                                    Movie movie = myGson.fromJson(arrayResponse.get(i).toString(), Movie.class);
                                    getView().getAdapter().addMovie(movie,maxItems);
                                }
                            }
                            if(!isScroll){
                                getView().onStopLoading();

                            } else {
                                getView().stopPaginationLoading();
                            }

                        } catch (Exception e) {
                            Log.e("ERROR:", "Error al parsear los datos");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        throw new RuntimeException( error);
                    }
                });
        volley.addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void filterByText(String value, Context ctx) {
        filterText = value;
        getView().getAdapter().clearList();
        if(TextUtils.isEmpty(value)){
            moviesRequest(ctx,1,POPULAR_URL);
        } else {
            moviesRequest(ctx,1,FILTER_URL+value);
        }



    }
}
