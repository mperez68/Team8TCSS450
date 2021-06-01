package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class ContactRequestsTabViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContactRequest>> mRequestList;
    private ContactRequestsTabRecyclerViewAdapter mViewAdapter;

    /**
     * Constructor the the contact list view model to instantiate instance fields.
     *
     * @param theApplication
     */
    public ContactRequestsTabViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mRequestList = new MutableLiveData<>();
        mRequestList.setValue(new ArrayList<>());
        mViewAdapter = new ContactRequestsTabRecyclerViewAdapter(mRequestList.getValue());
    }

    /**
     * Method that creates an observer for myContactList.
     *
     * @param theOwner
     * @param theObserver
     */
    public void addContactRequestObserver(@NonNull LifecycleOwner theOwner,
                                       @NonNull Observer<? super List<ContactRequest>> theObserver) {
        mRequestList.observe(theOwner, theObserver);
    }

    /**
     * Get the contactList.
     *
     * @return myContactList
     */
    public MutableLiveData<List<ContactRequest>> getContactList() {
        return mRequestList;
    }

    /**
     * Get view adapter
     *
     * @return myViewAdapter
     */
    public ContactRequestsTabRecyclerViewAdapter getViewAdapter() {
        return mViewAdapter;
    }
}
