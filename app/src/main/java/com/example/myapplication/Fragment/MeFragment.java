package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.AlterActivity;
import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Activity.UploadActivity;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    public static final String MESSAGE_STRING = "com.example.myapplication.UI.PERSON_INFO";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        final View view = inflater.inflate(R.layout.fragment_me, container, false);

        Button login_exit = view.findViewById(R.id.login_exit);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar_arrowRight);
        ImageView iv_realName = view.findViewById(R.id.iv_arrow_right_realName);
        ImageView iv_idNumber = view.findViewById(R.id.iv_arrow_right_idNumber);
        ImageView iv_gender = view.findViewById(R.id.iv_arrow_right_gender);
        ImageView iv_collageName = view.findViewById(R.id.iv_arrow_right_collegeName);
        ImageView iv_phone = view.findViewById(R.id.iv_arrow_right_phone);
        ImageView iv_inSchoolTime = view.findViewById(R.id.iv_arrow_right_inSchoolTime);
        ImageView iv_email = view.findViewById(R.id.iv_arrow_right_email);

        login_exit.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
        iv_realName.setOnClickListener(this);
        iv_idNumber.setOnClickListener(this);
        iv_gender.setOnClickListener(this);
        iv_collageName.setOnClickListener(this);
        iv_phone.setOnClickListener(this);
        iv_inSchoolTime.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AlterActivity.class);
        switch (view.getId()) {
            case R.id.iv_avatar_arrowRight:
                UploadActivity.isClickAvatar = true;
                startActivity(new Intent(getActivity(), UploadActivity.class));
                break;
            case R.id.iv_arrow_right_realName:
                intent.putExtra(MESSAGE_STRING, "??????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_idNumber:
                intent.putExtra(MESSAGE_STRING, "??????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_gender:
                intent.putExtra(MESSAGE_STRING, "??????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_collegeName:
                intent.putExtra(MESSAGE_STRING, "??????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_phone:
                intent.putExtra(MESSAGE_STRING, "?????????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_inSchoolTime:
                intent.putExtra(MESSAGE_STRING, "????????????");
                startActivity(intent);
                break;
            case R.id.iv_arrow_right_email:
                intent.putExtra(MESSAGE_STRING, "??????");
                startActivity(intent);
                break;
            case R.id.login_exit:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                requireActivity().finish();
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        TextView tv_id = requireView().findViewById(R.id.tv_id_info);
        TextView tv_username = requireView().findViewById(R.id.tv_username_info);
        TextView tv_realName = requireView().findViewById(R.id.tv_realName_info);
        TextView tv_idNumber = requireView().findViewById(R.id.tv_idNumber_info);
        TextView tv_gender = requireView().findViewById(R.id.tv_gender_info);
        TextView tv_collegeName = requireView().findViewById(R.id.tv_collegeName_info);
        TextView tv_phone = requireView().findViewById(R.id.tv_phone_info);
        TextView tv_inSchoolTime = requireView().findViewById(R.id.tv_inSchoolTime_info);
        TextView tv_email = requireView().findViewById(R.id.tv_email_info);
        ImageView iv_avatar = requireView().findViewById(R.id.ri_avatar);

        if (LoginData.loginUser.getAvatar() != null) {
            Picasso.get().load(LoginData.loginUser.getAvatar()).into(iv_avatar);
        }
        tv_id.setText(String.valueOf(LoginData.loginUser.getId()));
        tv_username.setText(LoginData.loginUser.getUsername());
        tv_realName.setText(LoginData.loginUser.getRealName());
        tv_idNumber.setText(String.valueOf(LoginData.loginUser.getIdNumber()));

        if (LoginData.loginUser.getGender()) {
            tv_gender.setText("???");
        } else {
            tv_gender.setText("???");
        }
        tv_collegeName.setText(LoginData.loginUser.getCollegeName());
        tv_phone.setText(LoginData.loginUser.getPhone());
        String tempStringDate = String.valueOf(LoginData.loginUser.getInSchoolTime());
        if (tempStringDate.length() == 8) {
            String realStringDate = tempStringDate.substring(0, 4) + "-" + tempStringDate.substring(4, 6)
                    + "-" + tempStringDate.substring(6, 8);
            tv_inSchoolTime.setText(realStringDate);
        } else {
            tv_inSchoolTime.setText("");
        }
        tv_email.setText(LoginData.loginUser.getEmail());
        super.onResume();
    }
}