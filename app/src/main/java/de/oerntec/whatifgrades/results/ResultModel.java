package de.oerntec.whatifgrades.results;

import java.util.ArrayList;
import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

public class ResultModel implements IResultModel {
    private List<Grade> mGradeList = new ArrayList<>();

    @Override
    public float getAverage() {
        float weightedSum = 0;
        for(Grade g : mGradeList){
            weightedSum += g.credits * g.getGradeFloat();
        }
        return weightedSum / mGradeList.size();
    }

    @Override
    public void add(List<Grade> list) {
        mGradeList.addAll(list);
    }

    @Override
    public void clear() {
        mGradeList = new ArrayList<>();
    }
}
