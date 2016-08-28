package de.oerntec.whatifgrades.grade_list;

import java.util.ArrayList;
import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

public class HypoGradeRamModel implements IHypoGradeModel {
    List<Grade> mGradeList = new ArrayList<>();

    @Override
    public void getGrades(OnFinishedListener listener) {
        listener.onFinished(mGradeList);
    }

    @Override
    public void addGrade(Grade grade, OnFinishedListener listener) {
        mGradeList.add(grade);
        listener.onFinished(mGradeList);
    }

    @Override
    public void removeGrade(Grade grade, OnFinishedListener listener) {
        for(int i = 0; i < mGradeList.size(); i++){
            if(mGradeList.get(i).id == grade.id)
                mGradeList.remove(i);
        }
        listener.onFinished(mGradeList);
    }

    @Override
    public void updateGrade(Grade grade, OnFinishedListener listener) {
        for(int i = 0; i < mGradeList.size(); i++){
            if(mGradeList.get(i).id == grade.id)
                mGradeList.set(i, grade);
        }
        listener.onFinished(mGradeList);
    }

    @Override
    public Grade getGrade(int position) {
        return mGradeList.get(position);
    }
}
