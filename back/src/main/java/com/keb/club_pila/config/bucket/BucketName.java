package com.keb.club_pila.config.bucket;

public enum BucketName {
    PROFILE_IMAGE("pilas-springboot-image-upload");
    private final String bucketName;
    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getBucketName(){
        return bucketName;
    }


}
