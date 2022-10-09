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
import android.widget.Toast;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentSignInActivity;
import com.example.myapplication.TeacherActivity.SignInformationActivity;
import com.example.myapplication.TeacherActivity.TeacherSignInActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherSigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherSigninFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherSigninFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherSigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherSigninFragment newInstance(String param1, String param2) {
        TeacherSigninFragment fragment = new TeacherSigninFragment();
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
        final View view = inflater.inflate(R.layout.fragment_teacher_signin, container, false);

        Button teaSign = view.findViewById(R.id.bt_enterUserCenter22);
        teaSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CourseData.Detail != null) {
                    startActivity(new Intent(getActivity(), SignInformationActivity.class));
                }else {
                    Toast.makeText(getActivity(),"需先查看课程详情！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}