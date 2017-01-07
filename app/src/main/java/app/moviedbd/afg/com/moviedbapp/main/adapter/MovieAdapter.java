package app.moviedbd.afg.com.moviedbapp.main.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.moviedbd.afg.com.moviedbapp.R;
import app.moviedbd.afg.com.moviedbapp.main.fragment.LoadMoreListener;
import app.moviedbd.afg.com.moviedbapp.main.fragment.MainFragmentView;
import app.moviedbd.afg.com.moviedbapp.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 03/01/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> {
    private static final int ITEMS_PER_PAGE = 20;

    private List<Movie> movieList;
    private Context ctx;
    private MainFragmentView mView;

    private boolean isLoading = false;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    private LoadMoreListener mOnLoadMoreListener;

    public MovieAdapter(final List<Movie> movieList, Context ctx, MainFragmentView view) {
        this.movieList = movieList;
        this.ctx = ctx;
        this.mView = view;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) view.getRecyclerView().getLayoutManager();
        mView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int page = 0;

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore((getItemCount()/ITEMS_PER_PAGE)+1);
                    }
                    isLoading = false;
                }
            }
        });
    }

    public void setOnLoadMoreListener(LoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public movieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_movie, parent, false);
        return new movieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(movieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvOverview.setText(movie.getOverview());
        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(TextUtils.isEmpty(movie.getReleaseDate())? "" : movie.getReleaseDate().substring(0,movie.getReleaseDate().indexOf("-")));
        String img = "http://image.tmdb.org/t/p/w92"+movie.getImgUrl()+"?api_key=2bf42b27584e8eb1ffea02e97a50ddd2";
        Glide.with(ctx).load(img).asBitmap().fitCenter().placeholder(R.drawable.ic_camera).into(holder.ivMovieImage);


        //Glide.with(ctx).load("http://image.tmdb.org/t/p/w92/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg?api_key=2bf42b27584e8eb1ffea02e97a50ddd2").asBitmap().centerCrop().into(holder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class movieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movieImage)
        ImageView ivMovieImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        public movieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addMovie(Movie movie, int maxItems) {
        if(movieList.size()< maxItems){
            movieList.add(movie);
            notifyDataSetChanged();
        }
    }

    public void clearList(){
        movieList.clear();
    }
}
