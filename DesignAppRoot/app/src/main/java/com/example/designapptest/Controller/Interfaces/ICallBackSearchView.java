package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.ClassOther.myFilter;


public interface ICallBackSearchView {
    public void addFilter(myFilter filter);
    public void replaceFilter(myFilter filter);
    public void removeFilter(myFilter filter);
}
