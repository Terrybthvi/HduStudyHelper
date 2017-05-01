package com.edu.hdu.hdustudyhelper.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author lizhangqu
 * 
 */
public class FileUtil {
	private Context context;

	public FileUtil(Context context) {
		this.context = context;
	}

	public boolean save(String filename, String content) {
		File file = new File(context.getFilesDir(), filename);
		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
