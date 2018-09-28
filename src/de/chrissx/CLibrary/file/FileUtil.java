package de.chrissx.CLibrary.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	public static void zip(List<String> inFileNames, String outFileName) throws IOException{
				ZipOutputStream zos = null; 
		        FileInputStream fis = null; 
		        try { 
		            zos = new ZipOutputStream(new FileOutputStream(outFileName)); 
		            fis = new FileInputStream(inFileNames.get(0)); 
		            zos.putNextEntry(new ZipEntry(new File(inFileNames.get(0)).getName())); 
		            int len; 
		            byte[] buffer = new byte[1024]; 
		            while ((len = fis.read(buffer, 0, buffer.length)) > 0) { 
		                zos.write(buffer, 0, len); 
		            } 
		        } catch (FileNotFoundException e) { 
		            e.printStackTrace(); 
		        } catch (IOException e) { 
		            e.printStackTrace(); 
		        }finally{ 
		            if(fis != null){ 
		                fis.close(); 
		            }
		            for(int i = 1; i < inFileNames.size(); i++) {
		            	String name = new File(inFileNames.get(i)).getName();
		            	addToZipFile(name, zos, inFileNames.get(i));
		            }
		            if(zos != null){
		                zos.closeEntry(); 
		                zos.close(); 
		            }
		        }
			}
	
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
					File destDir = new File(destDirectory);
			        if (!destDir.exists()) {
			            destDir.mkdir();
			        }
			        ZipInputStream zipIn;
					zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
					ZipEntry entry;
			        entry = zipIn.getNextEntry();
			        while (entry != null) {
			            String filePath = Paths.get(destDirectory, entry.getName()).toString();
			            if (!entry.isDirectory()) {
			                extractFile(zipIn, filePath);
			            } else {
			                File dir = new File(filePath);
			                dir.mkdir();
			            }
			            zipIn.closeEntry();
			            entry = zipIn.getNextEntry();
			        }
			    zipIn.close();
			}
	
	public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[1024];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
	
	public static List<File> list(File dir, String ext) {
		List<File> files = new ArrayList<File>();
		for(File f : dir.listFiles()) {
			if(f.getName().endsWith(ext)) {
				files.add(f);
			}
		}
		return files;
	}
	
	public static void addToZipFile(String fileName, ZipOutputStream zos, String fileOutPutName) throws FileNotFoundException, IOException {
		File file = new File(fileOutPutName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}
	
	public static String[] splitLines(String s) {
		return s.split("\n");
	}
}