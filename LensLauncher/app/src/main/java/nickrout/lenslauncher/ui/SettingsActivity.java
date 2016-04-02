package nickrout.lenslauncher.ui;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import nickrout.lenslauncher.R;
import nickrout.lenslauncher.util.Settings;

/**
 * Created by nickrout on 2016/04/02.
 */
public class SettingsActivity extends AppCompatActivity {

    private AppCompatSeekBar mLensDiameter;
    private TextView mValueLensDiameter;
    private AppCompatSeekBar mMinIconSize;
    private TextView mValueMinIconSize;
    private AppCompatSeekBar mDistortionFactor;
    private TextView mValueDistortionFactor;
    private AppCompatSeekBar mScaleFactor;
    private TextView mValueScaleFactor;
    private SwitchCompat mVibrateAppHover;
    private SwitchCompat mVibrateAppLaunch;
    private SwitchCompat mShowTouchSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupViews();
        assignValues();
        setTaskDescription();
    }

    private void setupViews() {
        Settings settings = new Settings(getBaseContext());
        mLensDiameter = (AppCompatSeekBar) findViewById(R.id.seek_bar_lens_diameter);
        mLensDiameter.setMax(settings.MAX_LENS_DIAMETER);
        mValueLensDiameter = (TextView) findViewById(R.id.value_lens_diameter);
        mLensDiameter.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String lensDiameter = progress + "dp";
                mValueLensDiameter.setText(lensDiameter);
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_LENS_DIAMETER, (float) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mMinIconSize = (AppCompatSeekBar) findViewById(R.id.seek_bar_min_icon_size);
        mMinIconSize.setMax(settings.MAX_MIN_ICON_SIZE);
        mValueMinIconSize = (TextView) findViewById(R.id.value_min_icon_size);
        mMinIconSize.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String minIconSize = progress + "dp";
                mValueMinIconSize.setText(minIconSize);
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_MIN_ICON_SIZE, (float) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mDistortionFactor = (AppCompatSeekBar) findViewById(R.id.seek_bar_distortion_factor);
        mDistortionFactor.setMax(settings.MAX_DISTORTION_FACTOR);
        mValueDistortionFactor = (TextView) findViewById(R.id.value_distortion_factor);
        mDistortionFactor.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String distortionFactor = progress + "";
                mValueDistortionFactor.setText(distortionFactor);
                float progressFloat = (float) progress;
                // TODO - Need to have condition (in her or in LensView) to prevent 0 distortion
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_DISTORTION_FACTOR, progressFloat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mScaleFactor = (AppCompatSeekBar) findViewById(R.id.seek_bar_scale_factor);
        mScaleFactor.setMax(settings.MAX_SCALE_FACTOR);
        mValueScaleFactor = (TextView) findViewById(R.id.value_scale_factor);
        mScaleFactor.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String scaleFactor = progress + "";
                mValueScaleFactor.setText(scaleFactor);
                // TODO - Need to have condition (in her or in LensView) to prevent 0 scale
                float progressFloat = (float) progress;
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_SCALE_FACTOR, progressFloat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mVibrateAppHover = (SwitchCompat) findViewById(R.id.switch_vibrate_app_hover);
        mVibrateAppHover.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_VIBRATE_APP_HOVER, isChecked);
            }
        });
        mVibrateAppLaunch = (SwitchCompat) findViewById(R.id.switch_vibrate_app_launch);
        mVibrateAppLaunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_VIBRATE_APP_LAUNCH, isChecked);
            }
        });
        mShowTouchSelection = (SwitchCompat) findViewById(R.id.switch_show_touch_selection);
        mShowTouchSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings settings = new Settings(getBaseContext());
                settings.save(Settings.KEY_SHOW_TOUCH_SELECTION, isChecked);
            }
        });
    }

    private void assignValues() {
        Settings settings = new Settings(getBaseContext());
        mLensDiameter.setProgress((int) settings.getFloat(Settings.KEY_LENS_DIAMETER));
        String lensDiameter = (int) settings.getFloat(Settings.KEY_LENS_DIAMETER) + "dp";
        mValueLensDiameter.setText(lensDiameter);
        mMinIconSize.setProgress((int) settings.getFloat(Settings.KEY_MIN_ICON_SIZE));
        String minIconSize = (int) settings.getFloat(Settings.KEY_MIN_ICON_SIZE) + "dp";
        mValueMinIconSize.setText(minIconSize);
        mDistortionFactor.setProgress((int) settings.getFloat(Settings.KEY_DISTORTION_FACTOR));
        String distortionFactor = (int) settings.getFloat(Settings.KEY_DISTORTION_FACTOR) + "";
        mValueDistortionFactor.setText(distortionFactor);
        mScaleFactor.setProgress((int) settings.getFloat(Settings.KEY_SCALE_FACTOR));
        String scaleFactor = (int) settings.getFloat(Settings.KEY_SCALE_FACTOR) + "";
        mValueScaleFactor.setText(scaleFactor);
        mVibrateAppHover.setChecked(settings.getBoolean(Settings.KEY_VIBRATE_APP_HOVER));
        mVibrateAppLaunch.setChecked(settings.getBoolean(Settings.KEY_VIBRATE_APP_LAUNCH));
        mShowTouchSelection.setChecked(settings.getBoolean(Settings.KEY_SHOW_TOUCH_SELECTION));
    }

    private void setTaskDescription() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap appIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    appIconBitmap,
                    ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
            setTaskDescription(taskDescription);
        }
    }
}