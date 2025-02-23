package com.zufar.icedlatte.common.filestorage.minio;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zufar.icedlatte.common.filestorage.exception.FileReadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioObjectUploader {

    private final AmazonS3 amazonS3;

    public void uploadFile(MultipartFile file, String bucketName, String fileName) {
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, fileName, inputStream, metadata);
        } catch (AmazonServiceException ase) {
            log.error("Minio couldn't process operation", ase);
            throw ase;
        } catch (SdkClientException sce) {
            log.error("Minio couldn't be contacted for a response", sce);
            throw sce;
        } catch (IOException e) {
            throw new FileReadException(fileName);
        }
    }
}
