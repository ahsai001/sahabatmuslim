package com.zaitunlabs.sahabatmuslim.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionManager;
import com.zaitunlabs.sahabatmuslim.R;
import com.zaitunlabs.zlcore.utils.CommonUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBoard1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OnBoard1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoard1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView;

    private OnFragmentInteractionListener mListener;

    public OnBoard1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnBoard1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnBoard1Fragment newInstance(String param1, String param2) {
        OnBoard1Fragment fragment = new OnBoard1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_board1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewGroup container = (ViewGroup)view;

        textView = container.findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            boolean rotate;
            @Override
            public void onClick(View view) {
                //TransitionManager.beginDelayedTransition((ViewGroup) container, new Rotate());
                rotate = !rotate;
                //textView.setRotation(rotate?180:0);
                textView.animate().rotation(rotate?180:0);

                CommonUtils.showPlacePicker(OnBoard1Fragment.this,1003);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Place place = CommonUtils.handlePlacePicker(OnBoard1Fragment.this,1003,requestCode,resultCode,data);
        if(place != null){
            CommonUtils.showInfo(getContext(),"",place.getName().toString());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
