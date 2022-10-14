package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.SelectSignInCourseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherSignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherSignInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TeacherSignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherSignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherSignInFragment newInstance(String param1, String param2) {
        TeacherSignInFragment fragment = new TeacherSignInFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_signin, container, false);

        Button teaSign = view.findViewById(R.id.bt_enterUserCenter22);
        teaSign.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SelectSignInCourseActivity.class));
        });
        // Inflate the layout for this fragment
        return view;
    }
}