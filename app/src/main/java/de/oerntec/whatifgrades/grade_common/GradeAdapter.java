package de.oerntec.whatifgrades.grade_common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.oerntec.whatifgrades.R;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    public interface IGradeClickedListener {
        void onGradeClicked(Grade grade);
    }

    private List<Grade> mItems = new LinkedList<>();
    private IGradeClickedListener mOnClickListener;

    public GradeAdapter(IGradeClickedListener onClickListener){
        mOnClickListener = onClickListener;
    }

    public void setItems(List<Grade> items){
        mItems = items;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_list_item, parent, false);

        return new GradeViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(final GradeViewHolder holder, final int position) {
        final Grade boundGrade = mItems.get(position);
        holder.bind(boundGrade);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onGradeClicked(boundGrade);
            }
        });
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.grade_list_item_credits) TextView creditView;
        @BindView(R.id.grade_list_item_grade) TextView gradeView;
        @BindView(R.id.grade_list_item_name) TextView nameView;

        public GradeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Grade g){
            creditView.setText(String.valueOf(g.credits));
            gradeView.setText(String.valueOf(g.grade));
            nameView.setText(g.name);
        }
    }
}
