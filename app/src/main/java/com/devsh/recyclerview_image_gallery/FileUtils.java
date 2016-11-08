package com.devsh.recyclerview_image_gallery;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Vector;

public class FileUtils {

	public File[] listFilesAsArray(File directory, FilenameFilter[] filter,
			int recurse) {
		Collection<File> files = listFiles(directory, filter, recurse);

		File[] arr = new File[files.size()];
		return files.toArray(arr);
	}

	public Collection<File> listFiles(File directory,
			FilenameFilter[] filter, int recurse) {

		Vector<File> files = new Vector<File>();

		File[] entries = directory.listFiles();

		if (entries != null) {
			for (File entry : entries) {
				for (FilenameFilter filefilter : filter) {
					if (filter == null
							|| filefilter
									.accept(directory, entry.getName())) {
						files.add(entry);
						Log.v("ImageViewFlipper", "Added: "
								+ entry.getName());
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