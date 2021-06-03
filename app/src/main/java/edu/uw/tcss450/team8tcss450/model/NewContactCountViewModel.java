package edu.uw.tcss450.team8tcss450.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class NewContactCountViewModel extends ViewModel {
    private MutableLiveData<Integer> mNewContactCount;

    public NewContactCountViewModel() {
        mNewContactCount = new MutableLiveData<>();
        mNewContactCount.setValue(0);
    }

    public void addContactCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewContactCount.observe(owner, observer);
    }

    public void increment() {
        mNewContactCount.setValue(mNewContactCount.getValue() + 1);
    }

    public void reset() {
        mNewContactCount.setValue(0);
    }
}
