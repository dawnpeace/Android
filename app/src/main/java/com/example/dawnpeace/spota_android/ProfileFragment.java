package com.example.dawnpeace.spota_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dawnpeace.spota_android.Classes.User;

public class ProfileFragment extends Fragment {

    private TextView tv_user_name;
    private TextView tv_user_identity_number;
    private TextView tv_email;
    private ImageView iv_profile;
    private SharedPrefHelper mSharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
        tv_user_identity_number = (TextView) v.findViewById(R.id.tv_users_identity_number);
        tv_email = (TextView) v.findViewById(R.id.tv_user_email);
        iv_profile = (ImageView) v.findViewById(R.id.iv_profile_img);


        mSharedPref = SharedPrefHelper.getInstance(getActivity());
        User user = mSharedPref.getUser();
        if(user != null){
            tv_user_name.setText(user.getName());
            tv_user_identity_number.setText(user.getIdentity_number());
            tv_email.setText(user.getEmail());
            String url = mSharedPref.getUser().getPictureUrl() == null ? "" : mSharedPref.getUser().getPictureUrl();
            if(!url.isEmpty()){
                Glide.with(getContext())
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv_profile);
            }
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(mSharedPref.getUser().getPictureUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_person_black_24dp)
                        .error(R.drawable.ic_vpn_key_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(iv_profile);
    }
}
