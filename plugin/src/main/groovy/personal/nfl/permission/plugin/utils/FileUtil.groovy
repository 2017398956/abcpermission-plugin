package personal.nfl.permission.plugin.utils


import java.nio.file.Files

class FileUtil {
    static List<File> listFiles(File dir, boolean recursion) {
        List<File> files = new ArrayList<>()
        if (dir != null && dir.exists()) {
            if (dir.isDirectory()) {
                if (recursion) {
                    for (File temp : dir.listFiles()) {
                        files.addAll(listFiles(temp, recursion))
                    }
                }
            } else {
                files.add(dir)
            }
        }
        return files
    }

    static List<File> listFiles(String dirPath, boolean recursion) {
        return listFiles(new File(dirPath), recursion)
    }

    static boolean copyFile(File srcFile, File destFile) {
        if (null == srcFile || destFile == null) {
            return false
        }
        try {
            Files.copy(srcFile.toPath(), destFile.toPath())
            println("AbcPermission: has copied file " + srcFile.absolutePath + " to " + destFile.absolutePath)
            return true
        } catch (Exception e) {
            e.printStackTrace()
        }
        return false
    }

    static boolean delete(File file) {
        if (file.exists()) {
            if (!file.delete()) {
                throw new Exception("AbcPermission: file cannot delete:" + file.getAbsolutePath())
            }
            println("AbcPermission: delete file " + file.absolutePath + " success")
            return true
        } else {
            return true
        }
    }
}