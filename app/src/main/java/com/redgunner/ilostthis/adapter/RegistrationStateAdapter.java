package com.redgunner.ilostthis.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.redgunner.ilostthis.view.fragment.auth.LogInFragment;
import com.redgunner.ilostthis.view.fragment.auth.SgInFragment;

public class RegistrationStateAdapter extends FragmentStateAdapter{


    public RegistrationStateAdapter (FragmentActivity fragmentActivity){
        super(fragmentActivity);

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new SgInFragment();
        }
        return new LogInFragment();
    }
}
