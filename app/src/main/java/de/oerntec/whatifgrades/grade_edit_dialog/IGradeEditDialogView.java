package de.oerntec.whatifgrades.grade_edit_dialog;

/**
 * Created by arne on 8/22/16.
 */
public interface IGradeEditDialogView {
    void setName(String name);
    void setCredits(int credits);
    void setGrade(int gradeIndex);
    void closeDialog();
}
