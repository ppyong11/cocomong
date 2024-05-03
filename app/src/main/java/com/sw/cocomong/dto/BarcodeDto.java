package com.sw.cocomong.dto;

import android.os.AsyncTask;

public class BarcodeDto {

    private String serviceId;  // 서비스명
    private String dataType;  // 요청파일타입
    private String startIdx;  // 요청시작위치
    private String endIdx;  // 요청종료위치
    private String brcdNo;  // 바코드 번호

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(String startIdx) {
        this.startIdx = startIdx;
    }

    public String getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(String endIdx) {
        this.endIdx = endIdx;
    }

    public String getBrcdNo() {
        return brcdNo;
    }

    public void setBrcdNo(String brcdNo) {
        this.brcdNo = brcdNo;
    }

    @Override
    public String toString() {
        return "BarcodeDto{" +
                "serviceId='" + serviceId + '\'' +
                ", dataType='" + dataType + '\'' +
                ", startIdx='" + startIdx + '\'' +
                ", endIdx='" + endIdx + '\'' +
                ", BRCD_NO='" + brcdNo + '\'' +
                '}';
    }
    public String toUrlString(){
        return
                "&serviceId=" + serviceId +
                "&dataType=" + dataType +
                "&startIdx=" + startIdx +
                "&endIdx=" + endIdx +
                "&BRCD_NO=" + brcdNo;
    }
}
