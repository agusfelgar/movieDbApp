package app.moviedbd.afg.com.moviedbapp.main.activity;

import app.moviedbd.afg.com.moviedbapp.domain.BasePresenter;
import app.moviedbd.afg.com.moviedbapp.domain.BaseView;

/**
 * Created by Usuario on 03/01/2017.
 */

public interface MainActivityContract {
    interface View extends BaseView<Presenter>{}
    interface Presenter extends BasePresenter{};
}
