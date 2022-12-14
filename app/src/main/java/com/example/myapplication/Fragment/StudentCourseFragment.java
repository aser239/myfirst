package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentCourseListActivity;
import com.example.myapplication.TeacherActivity.TeacherCourseListActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentCourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public StudentCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentCourseFragment newInstance(String param1, String param2) {
        StudentCourseFragment fragment = new StudentCourseFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student_course, container, false);

        Button enterSelectCourse = view.findViewById(R.id.enterSelectCourse222);
        enterSelectCourse.setOnClickListener(v -> startActivity(new Intent(getActivity(),
                TeacherCourseListActivity.class)));

        Button enterCourseList = view.findViewById(R.id.enterCourseList222);
        enterCourseList.setOnClickListener(v -> startActivity(new Intent(getActivity(),
                StudentCourseListActivity.class)));
        // Inflate the layout for this fragment
        return view;
    }
}