package de.oerntec.whatifgrades.main;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.oerntec.whatifgrades.R;
import de.oerntec.whatifgrades.grade_edit_dialog.IGradeEditDialogFinishListener;
import de.oerntec.whatifgrades.grade_common.Grade;
import de.oerntec.whatifgrades.grade_list.GradeView;
import de.oerntec.whatifgrades.results.ResultView;

public class MainActivity extends AppCompatActivity implements IGradeEditDialogFinishListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.main_act_fab)
    FloatingActionButton mFab;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private int mCurrentFragmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // fill the adapter with the appropriate fragments
        Bundle hypoBundle = new Bundle();
        hypoBundle.putBoolean("enable_user_editing", true);
        Bundle knownBundle = new Bundle();
        knownBundle.putBoolean("enable_user_editing", false);
        mSectionsPagerAdapter.addClass("endresult", ResultView.class.getName(), null);
        mSectionsPagerAdapter.addClass("known", GradeView.class.getName(), knownBundle);
        mSectionsPagerAdapter.addClass("Hypothetical", GradeView.class.getName(), hypoBundle);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_act_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabInterface clicked = (FabInterface) getCurrentFragment();
                clicked.onFabClicked();
            }
        });
    }

    private Fragment getCurrentFragment(){
        return getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.main_act_container+":"+mCurrentFragmentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onDialogFinishedUpdate(Grade grade) {
        ((IGradeEditDialogFinishListener) getCurrentFragment()).onDialogFinishedUpdate(grade);
    }

    @Override
    public void onDialogFinishedAdd(Grade grade) {
        ((IGradeEditDialogFinishListener) getCurrentFragment()).onDialogFinishedAdd(grade);
    }

    @Override
    public void onDialogFinishedDelete(Grade grade) {
        ((IGradeEditDialogFinishListener) getCurrentFragment()).onDialogFinishedDelete(grade);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}// dont care

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}//dont care

    @Override
    public void onPageSelected(int position) {
        mCurrentFragmentIndex = position;
        IRequireFab fragment = ((IRequireFab) getCurrentFragment());
        boolean enableFab = fragment.isFabRequired();
        mFab.setVisibility(enableFab ? View.VISIBLE : View.GONE);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        /**
         * Helper class to enable sorted key-value lists
         */
        private class Triple {
            public String key, value;
            public Bundle bundle;

            public Triple(String key, String value, Bundle bundle) {
                this.key = key;
                this.value = value;
                this.bundle = bundle;
            }
        }

        /**
         * This map will hold the known fragment class names, and their tab titles
         */
        private ArrayList<Triple> mClassnames = new ArrayList<>();

        /**
         * Context for instantiating fragments, set in constructor
         */
        final private Context mContext;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            // instantiate the appropriate class, contained as the value in a triple
            Triple data = mClassnames.get(position);
            return Fragment.instantiate(mContext, data.value, data.bundle);
        }

        @Override
        public int getCount() {
            // mClassnames holds all available tabs
            return mClassnames.size();
        }

        /**
         * Add a new possible tab/fragment class
         * @param tabTitle tab title to be shown
         * @param className name of the fragment class that should be instantiated
         */
        void addClass(String tabTitle, String className, Bundle bundle){
            mClassnames.add(new Triple(tabTitle, className, bundle));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position >= mClassnames.size())
                throw new InvalidParameterException("adapter position invalid");

            // the name is stored as key in a triple
            return mClassnames.get(position).key;
        }
    }
}
