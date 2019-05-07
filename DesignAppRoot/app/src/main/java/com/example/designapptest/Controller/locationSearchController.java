package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Adapters.AdapterRecyclerSuggestions;
import com.example.designapptest.Controller.Interfaces.IDistrictFilterModel;
import com.example.designapptest.Model.DistrictFilterModel;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class locationSearchController {
    Context context;
    DistrictFilterModel districtFilterModel;

    public locationSearchController(Context context){
        this.context = context;
        this.districtFilterModel = new DistrictFilterModel();
    }

    public void loadDistrictInData(RecyclerView recyclerSuggestion,String FilterString,boolean isSearchRoomCall){

        //Khởi tạo list lưu quận
        List<String> stringListDistrict = new ArrayList<String>();

        //Khởi tạo Adapter
        AdapterRecyclerSuggestions adapterRecyclerSuggestions = new AdapterRecyclerSuggestions(context, R.layout.element_location_recycler_view,stringListDistrict,isSearchRoomCall);
        //Set adapter
        recyclerSuggestion.setAdapter(adapterRecyclerSuggestions);

        //Tạo layout cho recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerSuggestion.setLayoutManager(layoutManager);


        IDistrictFilterModel iDistrictFilterModel = new IDistrictFilterModel() {
            @Override
            public void sendDistrict(String District) {
                stringListDistrict.add(District);
                adapterRecyclerSuggestions.notifyDataSetChanged();
            }
        };
        districtFilterModel.listDistrictLocation(FilterString,iDistrictFilterModel);


    }


}
