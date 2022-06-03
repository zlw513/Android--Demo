package com.zhlw.debug;

import static com.zhlw.module.common.constant.MConfigKt.STORE_USERNAME_KEY;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhlw.component.explore.R;
import com.zhlw.component.explore.ui.fragment.ExploreFragment;
import com.zhlw.module.common.utils.CCUtils;
import com.zhlw.module.common.utils.PreferencesUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DebugActivity extends AppCompatActivity {

    /**
     * 组件单独运行时，在其他组件中用MMKV存储的username信息，在这个组件还是读不到，因此此处在开发时，写死一个username。
     */
    private String loginUserName = "zlw513";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        PreferencesUtils.INSTANCE.put(STORE_USERNAME_KEY, loginUserName);

        ExploreFragment exploreFragment = (ExploreFragment) CCUtils.INSTANCE.getExploreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,exploreFragment).commitNow();

    }

}