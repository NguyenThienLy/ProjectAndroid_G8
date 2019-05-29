package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.Model.ViewModel;

import java.util.List;

public interface IViewModel {
    public void getViewInfo(ViewModel viewModel);
    public void setView();
    public void setLinearLayoutTopAllView(List<ViewModel> listViewsModel);
    public void setProgressBarLoadMore();
    public void setQuantityViews(int quantity);
}
