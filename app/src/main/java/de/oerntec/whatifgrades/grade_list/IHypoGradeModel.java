package de.oerntec.whatifgrades.grade_list;

import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

public interface IHypoGradeModel {
    interface OnFinishedListener {
        void onFinished(List<Grade> items);
    }

    void getGrades(OnFinishedListener listener);
    void addGrade(Grade grade, OnFinishedListener listener);
    void removeGrade(Grade grade, OnFinishedListener listener);
    void updateGrade(Grade grade, OnFinishedListener listener);
    Grade getGrade(int position);
}
