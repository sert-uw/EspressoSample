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

    public static final String PNG = ".png";
    public static final String JPG = ".jpg";
    public static final String BMP = ".bmp";

    public static void save(final Activity activity, String fileName, String fileType) {
        TakeScreenShot.save(activity, null, fileName, fileType);
    }

    public static void save(final Activity activity, String[] addDirNames, String fileName, String fileType) {
        String outputBasePath = createBasePath(activity, addDirNames);
        createDirs(outputBasePath);

        String filePath = outputBasePath + fileName + fileType;

        TakeScreenShot.save(activity, filePath);
    }

    private static void save(final Activity activity, final String filePath) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    DisplayMetrics dm = activity.getResources().getDisplayMetrics();
                    File file = new File(filePath);

                    Bitmap bitmap = Bitmap.createBitmap(
                            dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(bitmap);
                    activity.getWindow().getDecorView().draw(canvas);

                    OutputStream fos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.close();

                    String[] paths = {file.getAbsolutePath()};
                    String[] mimeTypes = {"image/png"};
                    MediaScannerConnection.scanFile(
                            activity.getApplicationContext(), paths, mimeTypes, null);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String createBasePath(Activity activity, String[] dirNames) {
        String createPath = BASE_PATH + activity.getPackageName() + "/";

        if (dirNames != null) {
            for (String dirName : dirNames) {
                createPath += dirName + "/";
            }
        }

        return createPath;
    }

    private static void createDirs(String path) {
        File dirs = new File(path);
        if (!dirs.exists())
            dirs.mkdirs();
    }
}
