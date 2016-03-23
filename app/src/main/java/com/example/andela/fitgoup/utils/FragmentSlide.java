package com.example.andela.fitgoup.utils;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andela on 23/03/2016.
 */
public class FragmentSlide extends Fragment {


  private static final String ARG_LAYOUT_RES_ID = "layoutResId";

  public static FragmentSlide newInstance(int layoutResId) {
    FragmentSlide sampleSlide = new FragmentSlide();

    Bundle args = new Bundle();
    args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
    sampleSlide.setArguments(args);

    return sampleSlide;
  }

  private int layoutResId;

  public FragmentSlide() {}

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
      layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(layoutResId, container, false);
  }
}
