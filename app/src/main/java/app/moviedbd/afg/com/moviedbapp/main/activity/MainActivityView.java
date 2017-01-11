package app.moviedbd.afg.com.moviedbapp.main.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import app.moviedbd.afg.com.moviedbapp.R;
import app.moviedbd.afg.com.moviedbapp.main.fragment.MainFragmentView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityView extends AppCompatActivity implements MainActivityContract.View, SearchView.OnQueryTextListener, filterListener {

    MainActivityContract.Presenter mPresenter;
    MainFragmentView mainFragment;

    @BindView(R.id.frameLayout_mainList)
    FrameLayout frameLayoutMainList;
    @BindView(R.id.activity_main_view)
    RelativeLayout activityMainView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainActivityPresenter(this);
        setContentView(R.layout.activity_main_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mainFragment = (MainFragmentView) getSupportFragmentManager().findFragmentById(R.id.frameLayout_main_fragment);

        if (mainFragment == null) {
            mainFragment = MainFragmentView.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_mainList, mainFragment).commit();
        }
    }

    public void setPresenter(MainActivityContract.Presenter presenter) {
        if (presenter != null) {
            this.mPresenter = presenter;
        } else {
            throw new RuntimeException("El presenter no puede ser nulo");
        }
    }

    MainActivityContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_action_search));
        searchView.setQueryHint(getText(R.string.action_search));

        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        filterBy(newText);
        return true;
    }

    @Override
    public void filterBy(String value) {
        mainFragment.filterByText(value);
    }
}
