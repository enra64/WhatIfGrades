package de.oerntec.whatifgrades.grade_edit_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.oerntec.whatifgrades.R;
import de.oerntec.whatifgrades.grade_common.Grade;

public class GradeEditDialogView extends AppCompatDialogFragment implements IGradeEditDialogView {
    @BindView(R.id.grade_edit_dialog_creditpicker) NumberPicker mCreditPicker;
    @BindView(R.id.grade_edit_dialog_gradepicker) NumberPicker mGradePicker;
    @BindView(R.id.grade_edit_dialog_name_edit) EditText mNameEdit;

    IGradeEditDialogPresenter mPresenter;

    public static GradeEditDialogView newInstance(@Nullable Grade grade) {
        GradeEditDialogView fragment = new GradeEditDialogView();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        if(grade != null){
            args.putInt("id", grade.id);
            args.putInt("credits", grade.credits);
            args.putFloat("grade", grade.grade);
            args.putString("name", grade.name);
        }
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View root = getActivity().getLayoutInflater().inflate(R.layout.grade_edit_dialog, null);
        builder.setView(root);

        ButterKnife.bind(this, root);

        // picker config
        mCreditPicker.setMaxValue(20);
        mGradePicker.setDisplayedValues(Grade.GRADE_NAMES);
        mGradePicker.setMaxValue(Grade.GRADE_NAMES.length - 1);

        // listeners for change
        mGradePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mPresenter.onGradeChanged(newVal);
            }
        });
        mCreditPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mPresenter.onCreditsChanged(newVal);
            }
        });
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.onNameChanged(s.toString());
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.onFinishClicked();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.onCancelClicked();
            }
        });

        // if this were an add dialog, no bundle arguments would be given
        if(getArguments().size() > 0){
            Bundle arg = getArguments();
            mPresenter = new GradeEditDialogPresenter(this, new Grade(arg.getInt("id"), arg.getInt("credits"), arg.getInt("grade"), arg.getString("name")));
            builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.onDeleteClicked();
                }
            });
        }
        // must be an add dialog
        else{
            mPresenter = new GradeEditDialogPresenter(this);
        }

        // check whether our activity implements the correct interface
        if(!(getActivity() instanceof IGradeEditDialogFinishListener))
            throw new ClassCastException(getActivity().toString() + " must implement IGradeEditDialogFinishListener");

        mPresenter.setHost((IGradeEditDialogFinishListener) getActivity());

        Dialog dialog = builder.create();
        mPresenter.getTitle(dialog);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setName(String name) {
        mNameEdit.setText(name);
    }

    @Override
    public void setCredits(int credits) {
        mCreditPicker.setValue(credits);
    }

    @Override
    public void setGrade(int gradeIndex) {
        mGradePicker.setValue(gradeIndex);
    }

    @Override
    public void closeDialog() {
        dismiss();
    }
}
