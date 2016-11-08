# Recyclerview-image-gallery
Android recyclerview Image gallery sample app

# Demo example

![](https://github.com/suhanlee/recyclerview-image-gallery/blob/master/screenshot/1.png)
![](https://github.com/suhanlee/recyclerview-image-gallery/blob/master/screenshot/2.png)
![](https://github.com/suhanlee/recyclerview-image-gallery/blob/master/screenshot/3.png)

# Image loader
- Glide([https://github.com/bumptech/glide](https://github.com/bumptech/glide))
```java
 Glide
                .with(mActivity)
                .load(mFileList.get(position))
                .override(200, 200)
                .centerCrop()
                .into(holder.imageResource);
```
# Image source
- Media files
``` java
private List<String> findMediaFiles() {
        List<String> fileList = new ArrayList<>();

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        //Stores all the images from the gallery in Cursor
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        //Total number of images
        int count = cursor.getCount();

        //Create an array to store path to all the images
        String[] arrPath = new String[count];

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            arrPath[i]= cursor.getString(dataColumnIndex);
            fileList.add(arrPath[i]);
        }

        return fileList;
    }
 ```
- External files(/sdcard)
```java
 private List<String> findImageFileInDirectory(String directory) {
        final List<String> tFileList = new ArrayList<String>();
        String[] imageTypes = {"jpg", "png" };
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

        FileUtils fileUtils = new FileUtils();
        File[] allMatchingFiles = fileUtils.listFilesAsArray(
                new File(directory), filter, -1);
        for (File f : allMatchingFiles) {
            tFileList.add(f.getAbsolutePath());
        }
        return tFileList;
    }
```
