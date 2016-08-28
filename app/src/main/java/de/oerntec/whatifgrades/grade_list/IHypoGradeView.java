package de.oerntec.whatifgrades.grade_list;

import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;
import de.oerntec.whatifgrades.main.FabInterface;

public interface IHypoGradeView extends FabInterface {
    void setGrades(List<Grade> gradeList);
    void showAddDialog();
    void showChangeDialog(Grade grade);
}
