package com.example.project_palm_on;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class GuideViewModel extends ViewModel {
    private final MutableLiveData<List<GuideItem>> guideItems = new MutableLiveData<>();

    public MutableLiveData<List<GuideItem>> getGuideItems() {
        return guideItems;
    }

    public void setGuideItems(List<GuideItem> guideItems) {
        this.guideItems.setValue(guideItems);
    }
}
