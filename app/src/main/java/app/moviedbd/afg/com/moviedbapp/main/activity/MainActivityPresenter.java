package app.moviedbd.afg.com.moviedbapp.main.activity;

/**
 * Created by Usuario on 03/01/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;

    public MainActivityPresenter(MainActivityContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        //getView().setPresenter(this);
    }

    @Override
    public void start() {
        //NOTHING TO DO HERE
        getView().setPresenter(this);
    }

    public MainActivityContract.View getView() {
        return mView;
    }
}
