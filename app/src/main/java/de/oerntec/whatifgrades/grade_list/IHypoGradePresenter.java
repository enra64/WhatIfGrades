package de.oerntec.whatifgrades.grade_list;

import de.oerntec.whatifgrades.grade_common.Grade;

/**
 * Created by arne on 8/22/16.
 */
public interface IHypoGradePresenter {
    void onItemClicked(Grade position);
    void onAddItemClicked();
    void onAddDialogFinished(Grade grade);
    void onChangeDialogFinished(Grade grade);
    void onDeleteDialog(Grade grade);
}