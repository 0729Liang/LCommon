package com.liang.lcommon.activity.demo.blog.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liang.lcommon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnotherRightFragment extends Fragment {


    public AnotherRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_another_right_fragment,container,false);
    }

}
