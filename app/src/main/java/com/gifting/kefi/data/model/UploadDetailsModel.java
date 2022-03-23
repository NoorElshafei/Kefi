package com.gifting.kefi.data.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadDetailsModel {

    private UploadTask uploadTask;
    private StorageReference storageReference;
    private ReelVideoModel reelVideoModel;
    private UploadsModel uploadsModel;
    private DatabaseReference videoId;

    public UploadDetailsModel(UploadTask uploadTask, StorageReference storageReference, ReelVideoModel reelVideoModel, UploadsModel uploadsModel) {
        this.uploadTask = uploadTask;
        this.storageReference = storageReference;
        this.reelVideoModel = reelVideoModel;
        this.uploadsModel = uploadsModel;

    }

    public UploadTask getUploadTask() {
        return uploadTask;
    }

    public void setUploadTask(UploadTask uploadTask) {
        this.uploadTask = uploadTask;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    public ReelVideoModel getReelVideoModel() {
        return reelVideoModel;
    }

    public void setReelVideoModel(ReelVideoModel reelVideoModel) {
        this.reelVideoModel = reelVideoModel;
    }

    public UploadsModel getUploadsModel() {
        return uploadsModel;
    }

    public void setUploadsModel(UploadsModel uploadsModel) {
        this.uploadsModel = uploadsModel;
    }


}
