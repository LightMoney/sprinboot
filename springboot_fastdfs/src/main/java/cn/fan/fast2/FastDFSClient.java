package cn.fan.fast2;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
 
@Component
public class FastDFSClient {
	@Autowired
	private FastFileStorageClient storageClient;


	// 文件上传
	public String uploadFile(String path) throws FileNotFoundException {
		File file = new File(path);
		InputStream input = new FileInputStream(file);
		long size = FileUtils.sizeOf(file);
		String name = file.getName();
		String fileName = name.substring(name.lastIndexOf("/") + 1);
		StorePath storePath = storageClient.uploadFile(input, size, FilenameUtils.getExtension(fileName), null);
		return storePath.getFullPath();
	}
	/**
	 * 上传文件
	 *
	 * @param file 文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public String uploadFile(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);

		return storePath.getGroup() + "/" + storePath.getPath();
	}
	// 文件删除
	public boolean deleteFile(String path) {
		try {
			StorePath storePath = StorePath.parseFromUrl(path);
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 下载文件
	 *  返回文件字节流大小
	 * @param fileUrl 文件URL
	 * @return 文件字节
	 * @throws IOException
	 */
	public byte[] downloadFile(String fileUrl) throws IOException {
		if (StringUtils.isEmpty(fileUrl)) {
			return new byte[1];
		}
		StorePath storePath = StorePath.parseFromUrl(fileUrl);
		DownloadByteArray downloadByteArray = new DownloadByteArray();
		byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
		return bytes;
	}
	// 文件下载
	public boolean downloadFile(String path, String downloadFilePath) throws IOException {
		File file = new File(downloadFilePath);
		FileOutputStream outputStream = null;
		// fastdfs 文件读取
		String filepath = path.substring(path.lastIndexOf("group1/") + 7);
		DownloadByteArray callback = new DownloadByteArray();
		byte[] content = storageClient.downloadFile("group1", filepath, callback);
		// 数据写入指定文件夹中
		outputStream = new FileOutputStream(file);
		outputStream.write(content);
		return true;
	}


}