package com.hussainabdelilah.javaserver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Service
public class AmazonClient {

    private AmazonS3 amazonS3;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public String uploadFile(MultipartFile multipartFile, String extension) {

        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName()+ "." + extension;
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void deleteFileFromS3Bucket(String fileKey) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName + "/", fileKey));
    }

    public byte[] getImage(String key) {
        //TODO change name of returned file
        S3Object fullObject = amazonS3.getObject(new GetObjectRequest(bucketName, key));
        InputStream in = fullObject.getObjectContent();

        byte[] imageBytes = null;
        try {
            BufferedImage imageFromAWS = ImageIO.read(in);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(imageFromAWS, "png", byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageBytes;

    }

    private void uploadFileTos3bucket(String fileName, File file) {
        this.amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.Private));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convFile;
    }

    private String generateFileName() {
        String id = UUID.randomUUID().toString();
        return id.replace("-", "_");
    }
}