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
import android.widget.Toast;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.AddCourseActivity;
import com.example.myapplication.TeacherActivity.FinishedCourseActivity;
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_course, container, false);

        Button enterTeacherHaveClassList = view.findViewById(R.id.enterTeacherHaveClassList);

        enterTeacherHaveClassList.setOnClickListener(view1 -> startActivity(new Intent(getActivity(),
                FinishedCourseActivity.class)));

        Button enterTeacherHaveNotClassList = view.findViewById(R.id.enterTeacherHaveNotClassList);
        enterTeacherHaveNotClassList.setOnClickListener(v -> startActivity(new Intent(getActivity(),
                UnfinishedCourseActivity.class)));

        Button enterTeacherAddCourse = view.findViewById(R.id.enterTeacherAddCourse);
        enterTeacherAddCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (LoginData.loginUser.getRealName() == null) {
                    Toast.makeText(getActivity(), "请先修改用户真实姓名！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getActivity(), AddCourseActivity.class));
                }
            }
        });
        return view;
    }
}