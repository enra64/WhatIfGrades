package de.oerntec.whatifgrades.results;

import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

/**
 * Created by arne on 8/23/16.
 */
public interface IResultModel {
    float getAverage();
    void add(List<Grade> list);
    void clear();
}
