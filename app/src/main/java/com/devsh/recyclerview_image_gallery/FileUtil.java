package com.devsh.recyclerview_image_gallery;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

class FileUtil {

	public static List<String> findMediaFiles(Context context) {
		List<String> fileList = new ArrayList<>();

		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;

		//Stores all the images from the gallery in Cursor
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy);

		if (cursor != null) {
			//Total number of images
			int count = cursor.getCount();

			//Create an array to store path to all the images
			String[] arrPath = new String[count];

			for (int i = 0; i < count; i++) {
				cursor.moveToPosition(i);
				int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

				arrPath[i] = cursor.getString(dataColumnIndex);
				fileList.add(arrPath[i]);
			}
			cursor.close();
		}
		return fileList;
	}

	public static List<String> findImageFileInDirectory(String directory, String[] imageTypes) {
		final List<String> tFileList = new ArrayList<>();
		FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

		int i = 0;
		for (final String type : imageTypes) {
			filter[i] = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith("." + type);
				}
			};
			i++;
		}

		File[] allMatchingFiles = listFilesAsArray(
				new File(directory), filter, -1);
		for (File f : allMatchingFiles) {
			tFileList.add(f.getAbsolutePath());
		}
		return tFileList;
	}

	private static File[] listFilesAsArray(File directory, FilenameFilter[] filter,
			int recurse) {
		Collection<File> files = listFiles(directory, filter, recurse);

		File[] arr = new File[files.size()];
		return files.toArray(arr);
	}

	private static Collection<File> listFiles(File directory, FilenameFilter[] filter, int recurse) {

		Vector<File> files = new Vector<>();

		File[] entries = directory.listFiles();

		if (entries != null) {
			for (File entry : entries) {
				for (FilenameFilter filefilter : filter) {
					if (filefilter.accept(directory, entry.getName())) {
						files.add(entry);
					}
				}
				if ((recurse <= -1) || (recurse > 0 && entry.isDirectory())) {
					recurse--;
					files.addAll(listFiles(entry, filter, recurse));
					recurse++;
				}
			}
		}
		return files;
	}
}