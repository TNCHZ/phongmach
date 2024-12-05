package com.example.jpyou.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jpyou.User.UserInformation;
import com.example.myapplication.R;

import java.util.List;

public class MedicalRecordAdapter extends BaseAdapter {

    private Context context;
    private Integer layout;
    private List<UserInformation> userInformation;


    public MedicalRecordAdapter(Context context, Integer layout, List<UserInformation> userInformation) {
        this.context = context;
        this.layout = layout;
        this.userInformation = userInformation;
    }

    //Lớp ViewHolder giúp tránh ánh xạ lặp đi lặp lại khi lướt lên xuống
    private class ViewHolder{
        TextView txtName;
        TextView txtDescribe;
        ImageView imgAvatar;
        Button btnComfirm;
    }

    @Override
    public int getCount() {return userInformation.size();}
    @Override
    public Object getItem(int i) {return null;}
    @Override
    public long getItemId(int i) {return 0;}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MedicalRecordAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null); //Lấy layout NurseRowSchedule
            holder = new MedicalRecordAdapter.ViewHolder();
            //Ánh xạ "id" từ view
            holder.txtName = view.findViewById(R.id.textViewName_RowSchedule);
            holder.txtDescribe = view.findViewById(R.id.textViewDescribe_RowSchedule);
            holder.btnComfirm = view.findViewById(R.id.btnConfirm_RowSchedule);
            view.setTag(holder); //Truyền trạng thái ánh xạ
        } else {
            holder = (MedicalRecordAdapter.ViewHolder) view.getTag();
        }

        //Gán giá trị
        UserInformation ps = userInformation.get(i);
        holder.txtName.setText(ps.getHoTen());
        holder.txtDescribe.setText(ps.getTxtDescribe());
        holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onConfirmClicked(ps); // Gọi callback với dữ liệu
                }
//                if (fragment == null || fragment.isRemoving()) {

//                    fragmentTransaction
//                            .add(Ridfragment, fragment)
//                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                            .addToBackStack(null)
//                            .commit();
//                    Log.e("Abstract", "DONE");
//                }
//                fragmentTransaction.replace(Ridfragment, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public interface OnConfirmClickListener {
        void onConfirmClicked(UserInformation user);
    }

    private OnConfirmClickListener listener;

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

}
