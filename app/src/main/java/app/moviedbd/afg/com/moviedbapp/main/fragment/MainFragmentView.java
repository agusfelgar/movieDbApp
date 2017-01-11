package app.moviedbd.afg.com.moviedbapp.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import app.moviedbd.afg.com.moviedbapp.R;
import app.moviedbd.afg.com.moviedbapp.main.adapter.MovieAdapter;
import app.moviedbd.afg.com.moviedbapp.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragmentView extends Fragment implements MainFragmentContract.View {

    MainFragmentContract.Presenter mPresenter;
    MovieAdapter movieAdapter;

    @BindView(R.id.rv_films)
    RecyclerView rvFilms;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.frameLayout_main_fragment)
    FrameLayout mFrameLayoutMainFragment;

    public MainFragmentView() {
        // Required empty public constructor
    }

    public MainFragmentContract.Presenter getPresenter(){
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        getPresenter().getMovies(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainFragmentPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initAdapter();
        setAdapter();
        movieAdapter.setOnLoadMoreListener(new LoadMoreListener(){
            @Override
            public void onLoadMore(int page) {
                getPresenter().loadMoreData(getContext(), page);
            }
        });
    }

    private void initAdapter() {
        if(movieAdapter == null){
            movieAdapter = new MovieAdapter(new ArrayList<Movie>(), this.getContext(),this);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layout = new LinearLayoutManager(this.getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        rvFilms.setLayoutManager(layout);
        rvFilms.setItemAnimator(new DefaultItemAnimator());
        ;
    }

    private void setAdapter() {
        rvFilms.setAdapter(movieAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setPresenter(MainFragmentContract.Presenter mPresenter) {
        if (mPresenter != null) {
            this.mPresenter = mPresenter;
        } else {
            throw new RuntimeException("El Presenter no puede ser nulo");
        }
    }

    public static MainFragmentView newInstance() {
        MainFragmentView fragment = new MainFragmentView();
        return fragment;
    }

    //region MANAGE VISIBILITY

    public void setProgressBarVisibility(Boolean visibility) {
        mProgressbar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setRootViewVisibility(Boolean visibility){
        mFrameLayoutMainFragment.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onInitLoading() {
        setProgressBarVisibility(true);
        setRootViewVisibility(false);
    }

    @Override
    public void onStopLoading() {
        setProgressBarVisibility(false);
        setRootViewVisibility(true);
    }

    // endregion

    @Override
    public MovieAdapter getAdapter(){
        return movieAdapter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return rvFilms;
    }

    @Override
    public void initPaginationLoading() {
        setProgressBarVisibility(true);
    }

    @Override
    public void stopPaginationLoading() {
        setProgressBarVisibility(false);
    }

    @Override
    public void filterByText(String value) {
        getPresenter().filterByText(value,getContext());
    }
}
