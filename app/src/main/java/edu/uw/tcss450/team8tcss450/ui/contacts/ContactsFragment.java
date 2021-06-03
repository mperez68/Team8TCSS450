package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabFragment;
import edu.uw.tcss450.team8tcss450.ui.contacts.requests.ContactRequestsTabFragment;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private ViewPager mViewPager;
    private ContactPageAdapter mPageAdapter;

    /**
     * empty constructor.
     */
    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate that binds the contact list view model and connects to the get endpoint on the server.
     *
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
    }

    /**
     * onCreate view that inflates the view with fragment contact.
     *
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     * @return the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        return theInflater.inflate(R.layout.fragment_contacts, theContainer, false);
    }

    /**
     * onViewCreated adds listeners to the front end elements and retrieves arguments
     * passed to the fragment.
     *
     * @param theView
     * @param theSavedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);
        mPageAdapter = new ContactPageAdapter(getChildFragmentManager());
        mViewPager = theView.findViewById(R.id.contact_view_pager);
        mViewPager.setAdapter(mPageAdapter);
        TabLayout tabLayout = theView.findViewById(R.id.contact_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        String[] tabNames = {"Contacts", "Requests"};
        for (int i = 0; i < tabNames.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabNames[i]);
        }
    }

    public class ContactPageAdapter extends FragmentStatePagerAdapter {

        /**
         * Constructor for the WeatherPageAdapter
         *
         * @param manager the manager for the pages of the view pager the adapter is connected to
         */
        public ContactPageAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ContactListTabFragment();
            } else {
                return new ContactRequestsTabFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}