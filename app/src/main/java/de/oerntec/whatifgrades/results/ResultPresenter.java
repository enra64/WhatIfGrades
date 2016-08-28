package de.oerntec.whatifgrades.results;

import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

/**
 * Created by arne on 8/23/16.
 */
public class ResultPresenter implements IResultPresenter {
    private IResultModel mModel;
    private IResultView mView;

    public ResultPresenter(IResultView view, IResultModel model){
        mModel = model;
        mView = view;
    }

    public void setModel(IResultModel model){
        mModel = model;
    }

    @Override
    public void onRefresh() {
        mView.showResult(String.valueOf(mModel.getAverage()));
    }
}
