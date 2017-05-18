package credo.ge.credoapp.views;

/**
 * Created by kaxge on 5/18/2017.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import credo.ge.credoapp.R;


/**
 * FloatingViewのサンプルサービスを削除するフラグメントです。
 */
public class DeleteActionFragment extends Fragment {

    /**
     * DeleteActionFragmentを生成します。
     *
     * @return DeleteActionFragment
     */
    public static DeleteActionFragment newInstance() {
        final DeleteActionFragment fragment = new DeleteActionFragment();
        return fragment;
    }

    /**
     * コンストラクタ
     */
    public DeleteActionFragment() {
        // Required empty public constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_delete_action, container, false);
        // 削除ボタン
        final View clearFloatingButton = rootView.findViewById(R.id.clearDemo);
        clearFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Easy way to delete a service
                final Activity activity = getActivity();
                activity.stopService(new Intent(activity, CustomFloatingViewService.class));
            }
        });
        return rootView;
    }
}