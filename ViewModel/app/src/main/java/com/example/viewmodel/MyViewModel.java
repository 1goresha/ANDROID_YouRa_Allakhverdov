package com.example.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
    private int count = 0;

    @Override
    protected void onCleared() {
        super.onCleared();

    }

    public MyViewModel() {
        super();
    }

    public void getIncrease(){
        mutableLiveData.setValue(++count);
    }

    public void getDecrease(){
        mutableLiveData.setValue(--count);
    }

    public MutableLiveData<Integer> getMutableLiveData(){
        mutableLiveData.setValue(count);
        return mutableLiveData;
    }
}
