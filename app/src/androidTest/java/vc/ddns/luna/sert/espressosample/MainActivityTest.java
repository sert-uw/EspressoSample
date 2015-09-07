package vc.ddns.luna.sert.espressosample;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sert on 15/09/07.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Activity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testScreenShot() {
        TakeScreenShot.save(mActivity, "test", TakeScreenShot.PNG);
    }
}
