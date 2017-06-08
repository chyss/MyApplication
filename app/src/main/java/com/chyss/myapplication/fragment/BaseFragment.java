package com.chyss.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;

import com.chyss.myapplication.R;

/**
 * @author chyss 2017-05-05
 */

public class BaseFragment extends Fragment
{
    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }
}
