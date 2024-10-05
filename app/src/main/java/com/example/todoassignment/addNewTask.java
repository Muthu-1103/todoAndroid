package com.example.todoassignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoassignment.Model.todoModel;
import com.example.todoassignment.utils.DBHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addNewTask extends BottomSheetDialogFragment {
    public static final String TAG="AddNewTask";
    private EditText mEditText;
    private Button mButton;
    private DBHelper myDB;
    public static addNewTask newInstance(){
        return  new addNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_new_task,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=view.findViewById(R.id.editText);
        mButton=view.findViewById(R.id.buttonsave);
        myDB=new DBHelper(getActivity());
        boolean isUpdate = false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);
            if(task.length()>0){
                mButton.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    mButton.setEnabled(false);
                    mButton.setBackgroundColor(Color.GRAY);
                }
                else{
                    mButton.setEnabled(true);
                    mButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        mButton.setOnClickListener(v->{
            String text=mEditText.getText().toString();
            if(finalIsUpdate){
                myDB.updateTask(bundle.getInt("id"),text);
            }
            else{
                todoModel item=new todoModel();
                item.setTask(text);
                item.setStatus(0);
                myDB.insertTask(item);
            }
            dismiss();
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof onDialogCloseListener){
            ((onDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
