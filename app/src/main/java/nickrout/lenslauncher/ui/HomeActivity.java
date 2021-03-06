package nickrout.lenslauncher.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import nickrout.lenslauncher.R;
import nickrout.lenslauncher.model.App;
import nickrout.lenslauncher.model.AppPersistent;
import nickrout.lenslauncher.util.ObservableObject;
import nickrout.lenslauncher.util.UpdateAppsTask;

/**
 * Created by nickrout on 2016/04/02.
 */
public class HomeActivity extends BaseActivity
        implements Observer, UpdateAppsTask.UpdateAppsTaskListener {

    private final static String TAG = "HomeActivity";

    @Bind(R.id.lens_view_apps)
    LensView mLensView;

    private PackageManager mPackageManager;
    private MaterialDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.workspace_bg));
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        ObservableObject.getInstance().addObserver(this);
        mPackageManager = getPackageManager();
        loadApps(true);

    }

    private void loadApps(boolean isLoad) {
        new UpdateAppsTask(mPackageManager, getApplicationContext(), getApplication(), isLoad, HomeActivity.this).execute();
    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }

    public static class AppsUpdatedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ObservableObject.getInstance().update();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .content(R.string.progress_loading_apps)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .show();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        ObservableObject.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object data) {
        loadApps(false);
    }

    @Override
    public void onUpdateAppsTaskPreExecute(boolean mIsLoad) {
        if (mIsLoad) {
            showProgressDialog();
        }
    }

    @Override
    public void onUpdateAppsTaskPostExecute(ArrayList<App> apps, ArrayList<Bitmap> appIcons) {
        for (int i = 0; i < apps.size(); i++) {
            if (!AppPersistent.getAppVisibility(
                    apps.get(i).getPackageName().toString(), apps.get(i).getName().toString())) {
                apps.remove(i);
                appIcons.remove(i);
                i--;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (HomeActivity.this.isDestroyed()) {
                return;
            }
        } else {
            if (HomeActivity.this.isFinishing()) {
                return;
            }
        }
        dismissProgressDialog();
        mLensView.setPackageManager(mPackageManager);
        mLensView.setApps(apps, appIcons);
    }
}