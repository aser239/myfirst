package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Activity.MessageActivity;
import com.example.myapplication.Activity.WelcomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.AddCourseActivity;
import com.example.myapplication.TeacherActivity.FinishedCourseActivity;
import com.example.myapplication.TeacherActivity.TeacherCourseActivity;
import com.example.myapplication.TeacherActivity.UnfinishedCourseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherCourseFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherCourseFragment newInstance(String param1, String param2) {
        TeacherCourseFragment fragment = new TeacherCourseFragment();
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

        final View view = inflater.inflate(R.layout.fragment_teacher_course, container, false);

        Button enterTeacherHaveClassList = view.findViewById(R.id.enterTeacherHaveClassList22);

        enterTeacherHaveClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FinishedCourseActivity.class));
            }
        });

        Button enterTeacherHaveNotClassList = view.findViewById(R.id.enterTeacherHaveNotClassList22);
        enterTeacherHaveNotClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UnfinishedCourseActivity.class));
            }
        });


        Button addCourse = view.findViewById(R.id.enterTeacherAddCourse22);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCourseActivity.class));
            }
        });

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_teacher_course, container, false);
        return view;
    }
}