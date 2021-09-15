package com.keb.club_pila.config.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class FileStore {
    //public static final String CLOUDFRONT_DOMAIN_NAME="d1djtzszdq7pt7.cloudfront.net";
    
    private final AmazonS3 s3;
    /*s3 파일 저장*/
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetaData,
                    InputStream inputStream) throws IOException //path=버킷이름
   {
       ObjectMetadata metadata=new ObjectMetadata();
       byte[] bytes=IOUtils.toByteArray(inputStream);
       metadata.setContentLength(bytes.length);
       ByteArrayInputStream byteArrayIS=new ByteArrayInputStream(bytes);
       optionalMetaData.ifPresent(map->{
           if(!map.isEmpty()){
               map.forEach(metadata::addUserMetadata);
           }
       }

       );
       try{
           //s3 클라이언트가 오브젝트를 s3에 업로드한다
           s3.putObject(path, fileName, byteArrayIS, metadata);
       }catch(AmazonServiceException e){
           throw new IllegalStateException("Failed to store file to s3", e);
       }
   }

    public byte[] downloadFromS3(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }
    public void delete(String path, String fileName){
        try{
            boolean isExistObject=s3.doesObjectExist(path, fileName);
           if(isExistObject){s3.deleteObject(path, fileName);}
        }catch(AmazonServiceException e){
            throw new IllegalStateException("Failed to delete file from s3", e);
        }
    }
}
