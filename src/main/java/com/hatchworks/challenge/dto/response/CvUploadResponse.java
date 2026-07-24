package com.hatchworks.challenge.dto.response;

public class CvUploadResponse {

    private boolean success;
    private CvDataDto data;

    public CvUploadResponse() {
    }

    public CvUploadResponse(boolean success, CvDataDto data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CvDataDto getData() {
        return data;
    }

    public void setData(CvDataDto data) {
        this.data = data;
    }
}