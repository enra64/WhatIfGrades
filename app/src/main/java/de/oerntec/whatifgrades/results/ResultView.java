package de.oerntec.whatifgrades.results;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.oerntec.whatifgrades.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultView extends Fragment implements IResultView{
    @BindView(R.id.result_frag_main_text)
    TextView mGiantText;

    IResultPresenter mPresenter;

    public ResultView() {
        // Required empty public constructor
    }

    public static ResultView newInstance(Bundle bundle) {
        ResultView fragment = new ResultView();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setModel(IResultModel model){
        ((ResultPresenter) mPresenter).setModel(model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.result_frag, container, false);
        ButterKnife.bind(this, root);
        mPresenter = new ResultPresenter(this, null);
        return root;
    }

    @Override
    public void showResult(String result) {
        mGiantText.setText(result);
    }
}
