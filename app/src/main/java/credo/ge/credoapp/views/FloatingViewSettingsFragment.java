package credo.ge.credoapp.views;

/**
 * Created by kaxge on 5/18/2017.
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;

import credo.ge.credoapp.R;

/**
 * FloatingViewの設定を行います。
 */
public class FloatingViewSettingsFragment extends PreferenceFragment {

    /**
     * FloatingViewSettingsFragmentを生成します。
     *
     * @return FloatingViewSettingsFragment
     */
    public static FloatingViewSettingsFragment newInstance() {
        final FloatingViewSettingsFragment fragment = new FloatingViewSettingsFragment();
        return fragment;
    }

    /**
     * コンストラクタ
     */
    public FloatingViewSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * {@inheritDoc}
     */
}