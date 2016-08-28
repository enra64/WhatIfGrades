package de.oerntec.whatifgrades.grade_edit_dialog;

import android.app.Dialog;

import de.oerntec.whatifgrades.grade_common.Grade;

public class GradeEditDialogPresenter implements IGradeEditDialogPresenter {
    public enum DialogType {
        ADD_DIALOG,
        CHANGE_DIALOG
    }

    final DialogType mDialogType;
    IGradeEditDialogFinishListener mFinishListener;
    Grade mModel;
    IGradeEditDialogView mView;

    public GradeEditDialogPresenter(IGradeEditDialogView view){
        mView = view;
        mModel = new Grade(-1, 5, 1, "");
        mDialogType = DialogType.ADD_DIALOG;
    }

    public GradeEditDialogPresenter(IGradeEditDialogView view, Grade grade){
        mView = view;
        mModel = grade;
        mDialogType = DialogType.CHANGE_DIALOG;
    }

    @Override
    public void onNameChanged(String name) {
        mModel.name = name;
    }

    @Override
    public void onGradeChanged(int grade) {
        mModel.grade = grade;
    }

    @Override
    public void onCreditsChanged(int credits) {
        mModel.credits = credits;
    }

    @Override
    public void onFinishClicked() {
        switch(mDialogType){
            case ADD_DIALOG:
                mFinishListener.onDialogFinishedAdd(mModel);
                break;
            case CHANGE_DIALOG:
                mFinishListener.onDialogFinishedUpdate(mModel);
                break;
        }
        mView.closeDialog();
    }

    @Override
    public void onCancelClicked() {
        mView.closeDialog();
    }

    @Override
    public void onDeleteClicked() {
        mFinishListener.onDialogFinishedDelete(mModel);
        mView.closeDialog();
    }

    @Override
    public void setHost(IGradeEditDialogFinishListener listener) {
        mFinishListener = listener;
    }

    @Override
    public void getTitle(Dialog d) {
        d.setTitle(mDialogType == DialogType.ADD_DIALOG ? "Add grade" : "Update grade");
    }
}
