package com.csye6225.demo.controllers;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class UploadFileToS3Bucket {


    public void uploadFile(MultipartFile multipartfile){
       // String endpointURL = System.getenv("spring.datasource.url");
       // String username = System.getProperty("spring.datasource.username");
        String bucketName = System.getProperty("bucket.name");


        System.out.println("bucket name is :" + bucketName);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).build();
        try{
            System.out.println("Uploading file to s3 bucket");
            File filename = convertFromMultipart(multipartfile);
            s3Client.putObject(new PutObjectRequest(bucketName,filename.getName(),filename));
        }catch(AmazonServiceException ase){
            System.out.println("bucket name: " + bucketName);
            System.out.println("Request made to s3 bucket failed");

            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());

            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());

            System.out.println("Request ID:       " + ase.getRequestId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public File convertFromMultipart(MultipartFile file) throws Exception {
        File newFile = new File(file.getOriginalFilename());
        newFile.createNewFile();
        FileOutputStream fs = new FileOutputStream(newFile);
        fs.write(file.getBytes());
        fs.close();
        return newFile;
    }

}
