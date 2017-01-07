package app.moviedbd.afg.com.moviedbapp.domain;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Usuario on 03/01/2017.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private static Context ctx;
    private RequestQueue rq;

    public VolleySingleton(Context ctx){
        this.ctx = ctx;
        this.rq = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context ctx){
        if (volleySingleton == null){
            volleySingleton = new VolleySingleton(ctx);
        }
        return volleySingleton;
    }

    public RequestQueue getRequestQueue(){
        if (rq == null){
            rq = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return rq;
    }

    public void addToRequestQueue(Request req) {
        rq.add(req);
    }
}
