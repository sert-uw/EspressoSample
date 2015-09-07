package vc.ddns.luna.sert.espressosample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by sert on 15/09/07.
 */
public class TakeScreenShot {
    private static final String BASE_PATH
            = Environment.getExternalStorageDirectory() + "/AndroidTestSS/";

    private String outputBasePath;
    private Activity mActivity;

    public TakeScreenShot(Activity activity) {
        mActivity = activity;

        outputBasePath = BASE_PATH + activity.getPackageName() + "/";

        File dirs = new File(outputBasePath);
        if (!dirs.exists())
            dirs.mkdirs();
    }

    public void takeScreenShot(String fileName) {
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();

        final Bitmap bitmap = Bitmap.createBitmap(
                dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        final File file = new File(fileNameValidation(fileName));

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Canvas canvas = new Canvas(bitmap);
                    mActivity.getWindow().getDecorView().draw(canvas);

                    OutputStream fos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.close();

                    String[] paths = {file.getAbsolutePath()};
                    String[] mimeTypes = {"image/png"};
                    MediaScannerConnection.scanFile(
                            mActivity.getApplicationContext(), paths, mimeTypes, null);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String fileNameValidation(String fileName) {
        String outputName;

        if (fileName.indexOf('.') == -1) {
            outputName = fileName + ".png";
        } else {
            outputName = fileName;
        }

        return outputBasePath + outputName;
    }
}
