package de.oerntec.whatifgrades.grade_list;

import java.util.List;

import de.oerntec.whatifgrades.grade_common.Grade;

public class HypoGradePresenter implements IHypoGradePresenter, IHypoGradeModel.OnFinishedListener {
    private IHypoGradeModel mModel;
    private IHypoGradeView mView;

    public HypoGradePresenter(IHypoGradeModel mModel, IHypoGradeView mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void onItemClicked(Grade grade) {
        mView.showChangeDialog(grade);
    }

    @Override
    public void onAddItemClicked() {
        mView.showAddDialog();
    }

    @Override
    public void onAddDialogFinished(Grade grade) {
        mModel.addGrade(grade, this);
    }

    @Override
    public void onChangeDialogFinished(Grade grade) {
        mModel.updateGrade(grade, this);
    }

    @Override
    public void onDeleteDialog(Grade grade) {
        mModel.removeGrade(grade, this);
    }

    @Override
    public void onFinished(List<Grade> items) {
        mView.setGrades(items);
    }
}
