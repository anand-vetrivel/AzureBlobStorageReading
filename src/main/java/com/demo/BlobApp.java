package com.demo;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class BlobApp {
    private static final String CONNECTION_STRING_FOR_BLOB_STORAGE = "<BLOB_STORAGE_CONNECTION_STRING>";
    private static final String CONTAINER_REF = "harmony/tenant/91eb8dee-8dc6-4c83-86fb-74697880b90f/patients/113473741b100843f4cd81";
    private static final String BLOB_FILENAME = "I2CCARD.BIN";

    public static void main(String[] args) throws URISyntaxException, InvalidKeyException, StorageException {

        CloudStorageAccount account = CloudStorageAccount.parse(CONNECTION_STRING_FOR_BLOB_STORAGE);
        CloudBlobClient client = account.createCloudBlobClient();
        CloudBlobContainer container = client.getContainerReference(CONTAINER_REF);
        CloudBlobDirectory directory = container.getDirectoryReference("");

        byte[] i2c = readDataAsbytes(directory, BLOB_FILENAME);
        System.out.println("i2c bytes >>> " + i2c);
        System.out.println("i2c Arrays.toString >>> " + Arrays.toString(i2c));
    }

    private static byte[] readDataAsbytes(CloudBlobDirectory directory,String file) throws StorageException, URISyntaxException {
        CloudBlockBlob blobfile = directory.getBlockBlobReference(file);
        System.out.println("blobFile URI >> " + blobfile.getUri());
        if(!blobfile.exists()) {
            System.err.println("blob file don't exist !");
            return null;
        }
        Long fileLength = blobfile.getProperties().getLength();
        byte[] bytes = new byte[fileLength.intValue()];
        blobfile.downloadToByteArray(bytes, 0);
        if (bytes.length == 0) {
            System.err.println("bytes length = 0 !");
            return null;
        }
        System.out.println("SSH32 POC file = " + file + ", byte[] size = " + bytes.length);
        return bytes;
    }
}
