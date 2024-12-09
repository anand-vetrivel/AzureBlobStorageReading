package com.azure.blob.read.demo;

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
    private static final String CONNECTION_STRING_FOR_BLOB_STORAGE = "RGVmYXVsdEVuZHBvaW50c1Byb3RvY29sPWh0dHBzO0FjY291bnROYW1lPW10aHN0dHJlYXRtZW50ZmlsZXNldXNkO0FjY291bnRLZXk9OTNhS3R3aHNqWlltSVpBSlRjOGhlR3p3L1lZKzEvbjJFVEF0ellURTJuUjNRazg2K0FtaUNtaEVNOHhWa00vQSt2bXdCaTQxK01nUytBU3R6Q1BEUWc9PTtFbmRwb2ludFN1ZmZpeD1jb3JlLndpbmRvd3MubmV0";
    private static final String CONTAINER_REF = "aGFybW9ueS90ZW5hbnQvOTFlYjhkZWUtOGRjNi00YzgzLTg2ZmItNzQ2OTc4ODBiOTBmL3BhdGllbnRzLzExMzQ3Mzc0MWIxMDA4NDNmNGNkODE";
    private static final String BLOB_FILENAME = "STJDQ0FSRC5CSU4";

    public static void main(String[] args) throws URISyntaxException, InvalidKeyException, StorageException {

        CloudStorageAccount account = CloudStorageAccount.parse(CONNECTION_STRING_FOR_BLOB_STORAGE);
        CloudBlobClient client = account.createCloudBlobClient();
        CloudBlobContainer container = client.getContainerReference(CONTAINER_REF);
        CloudBlobDirectory directory = container.getDirectoryReference("");

        byte[] bytesArr = readDataAsbytes(directory, BLOB_FILENAME);
        System.out.println("bytesArr >>> " + bytesArr);
        System.out.println("bytesArr Arrays.toString >>> " + Arrays.toString(bytesArr));
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
        System.out.println("POC file = " + file + ", byte[] size = " + bytes.length);
        return bytes;
    }
}
