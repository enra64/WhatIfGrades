package de.oerntec.whatifgrades.grade_edit_dialog;

import de.oerntec.whatifgrades.grade_common.Grade;

/**
 * Created by arne on 8/22/16.
 */
public interface IGradeEditDialogFinishListener {
    void onDialogFinishedUpdate(Grade grade);
    void onDialogFinishedAdd(Grade grade);
    void onDialogFinishedDelete(Grade grade);
}
