package de.oerntec.whatifgrades.grade_edit_dialog;

import android.app.Dialog;

/**
 * Created by arne on 8/22/16.
 */
public interface IGradeEditDialogPresenter {
    void onNameChanged(String name);
    void onGradeChanged(int grade);
    void onCreditsChanged(int credits);
    void onFinishClicked();
    void onCancelClicked();
    void onDeleteClicked();
    void setHost(IGradeEditDialogFinishListener listener);

    void getTitle(Dialog d);
}
