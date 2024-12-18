package com.game.utils;

import com.game.config.OrangeWebConfig;
import com.game.exception.FileNameLengthLimitExceededException;
import com.game.exception.FileSizeLimitExceededException;
import com.game.exception.InvalidExtensionException;
import com.game.player.structs.WebPlayer;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class FileUploadUtils {
    /**
     * 默认大小 1M
     */
    public static final long DEFAULT_MAX_SIZE = 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9_.\\- ]");

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws com.game.exception.FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException              文件名太长
     * @throws IOException                                       比如读写文件出错时
     * @throws InvalidExtensionException                         文件校验异常
     */
    public static String upload(WebPlayer player, String baseDir, MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        // 检查文件名长度
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        int fileNameLength = Objects.requireNonNull(originalFilename).length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        // 检查文件后缀
        assertAllowed(file, allowedExtension);
        // 清理文件名
        String cleanedFileName = cleanFileName(originalFilename);
        // 文件名编码
        String fileName = extractFilename(file, cleanedFileName);
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        // 记录头像存储地址
        player.setAvatarPath(absPath);
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 清理文件名中的特殊字符和中文。
     *
     * @param filename 原始文件名
     * @return 清理后的文件名
     */
    private static String cleanFileName(String filename) {
        // 替换特殊字符和中文字符
        String cleanedName = SPECIAL_CHAR_PATTERN.matcher(filename).replaceAll("");
        // 替换连续的空格为单个空格
        cleanedName = cleanedName.replaceAll("\\s+", " ").trim();
        // 替换空格为下划线
        cleanedName = cleanedName.replace(' ', '_');
        return cleanedName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file, String fileName) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(), FilenameUtils.getBaseName(fileName), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    /**
     * 此方法用于上传游戏配置文件和后台头像，当上传的时候配置有些是以/结尾的，后台头像路径不是以/结尾的，所以需要特殊处理
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = OrangeWebConfig.getProfile().length();
        // 把所有以/结尾的去掉
        if(uploadDir.endsWith("/")){
            uploadDir = StringUtils.substring(uploadDir, 0, uploadDir.length() - 1);
        }
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension, fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
}