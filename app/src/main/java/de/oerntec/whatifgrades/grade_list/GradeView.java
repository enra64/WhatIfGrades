package de.oerntec.whatifgrades.grade_list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.oerntec.whatifgrades.R;
import de.oerntec.whatifgrades.grade_common.Grade;
import de.oerntec.whatifgrades.grade_common.GradeAdapter;
import de.oerntec.whatifgrades.grade_edit_dialog.GradeEditDialogView;
import de.oerntec.whatifgrades.grade_edit_dialog.IGradeEditDialogFinishListener;
import de.oerntec.whatifgrades.main.IRequireFab;

public class GradeView extends Fragment implements IHypoGradeView, IGradeEditDialogFinishListener, IRequireFab, GradeAdapter.IGradeClickedListener {
    private IHypoGradePresenter mPresenter;

    @BindView(R.id.hypo_grade_frag_list)
    RecyclerView mRecyclerView;
    private boolean mEnableUserEditing;

    private GradeAdapter mAdapter;

    public GradeView() {
    }

    public static GradeView newInstance(Bundle bundle) {
        GradeView fragment = new GradeView();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new HypoGradePresenter(new HypoGradeRamModel(), this);
        View root = inflater.inflate(R.layout.hypo_grade_frag, container, false);
        ButterKnife.bind(this, root);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new GradeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mEnableUserEditing = getArguments().getBoolean("enable_user_editing");
    }

    @Override
    public void setGrades(List<Grade> gradeList) {
        mAdapter.setItems(gradeList);
    }

    @Override
    public void showAddDialog() {
        GradeEditDialogView d = GradeEditDialogView.newInstance(null);
        d.show(getChildFragmentManager(), "editDialog");
    }

    @Override
    public void showChangeDialog(Grade grade) {
        GradeEditDialogView d = GradeEditDialogView.newInstance(grade);
        d.show(getChildFragmentManager(), "editDialog");
    }

    @Override
    public void onFabClicked() {
        mPresenter.onAddItemClicked();
    }

    @Override
    public void onDialogFinishedUpdate(Grade grade) {
        mPresenter.onChangeDialogFinished(grade);
    }

    @Override
    public void onDialogFinishedAdd(Grade grade) {
        mPresenter.onAddDialogFinished(grade);
    }

    @Override
    public void onDialogFinishedDelete(Grade grade) {
        mPresenter.onDeleteDialog(grade);
    }

    @Override
    public void onGradeClicked(Grade grade) {
        mPresenter.onItemClicked(grade);
    }

    @Override
    public boolean isFabRequired() {
        return mEnableUserEditing;
    }
}
