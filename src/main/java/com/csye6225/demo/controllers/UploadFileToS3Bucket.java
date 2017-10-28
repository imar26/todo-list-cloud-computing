package com.csye6225.demo.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class UploadFileToS3Bucket {

    private static String bucketName = "code-deploy.siddhantchandiwal.me";
    private static String keyName = "AKIAIITHJ4LFNWYTA3HQ";
    private static String fileName = "/home/siddhant/Desktop/Cloud_Old_Shell_Script.sh";

    public void uploadFile(MultipartFile multipartFile){
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

        try{
            System.out.println("Uploading file to S3 bucket");

            File fileName = convertFromMultipart(multipartFile);
            s3client.putObject(new PutObjectRequest(bucketName, fileName.getName(), fileName));
            System.out.println("File Uploaded to S3 bucket Successfully!!!");
        }catch(AmazonServiceException a){
            System.out.println("Request to S3 bucket failed");
            System.out.println("Error Message:       " + a.getErrorMessage());
            System.out.println("HTTP Status Code:    " + a.getStatusCode());
            System.out.println("AWS Error Code:      " + a.getErrorCode());
            System.out.println("AWS Error Type:      " + a.getErrorType());
            System.out.println("Request ID:          " + a.getRequestId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public File convertFromMultipart(MultipartFile file) throws Exception{
        File convertedFile = new File(file.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}
