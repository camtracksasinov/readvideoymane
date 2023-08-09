//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	@Value("${upload.path}")
	public String uploadDir;

	public void uploadFile(final MultipartFile file, final String filename) {
		try {
			final Path copyLocation = Paths.get(this.uploadDir + File.separator + StringUtils.cleanPath(filename),
					new String[0]);
			final File directory = new File(this.uploadDir);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
