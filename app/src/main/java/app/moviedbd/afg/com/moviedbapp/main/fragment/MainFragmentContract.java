package app.moviedbd.afg.com.moviedbapp.main.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import app.moviedbd.afg.com.moviedbapp.domain.BasePresenter;
import app.moviedbd.afg.com.moviedbapp.domain.BaseView;
import app.moviedbd.afg.com.moviedbapp.main.adapter.MovieAdapter;

/**
 * Created by Usuario on 03/01/2017.
 */

public interface MainFragmentContract {
    interface View extends BaseView<Presenter>{
        void onInitLoading();
        void onStopLoading();
        MovieAdapter getAdapter();
        RecyclerView getRecyclerView();
        void initPaginationLoading();
        void stopPaginationLoading();

        void filterByText(String value);
    }
    interface Presenter extends BasePresenter {
        void getMovies(Context ctx);
        void loadMoreData(Context ctx, int page);

        void filterByText(String value, Context ctx);
    }
}
