package com.veryclone.common.view;

import java.util.Date;

/**
 * 脚本视图对象
 * 包含pl_script表所有字段和关联表的info字段
 */
public class PlScriptView {
    // pl_script表字段
    private Integer id;
    private Integer videoId;
    private Integer audioId;
    private String beginTxt;
    private String endTxt;
    private Integer serviceId;
    private Integer linkId;
    private Integer analysisId;
    private Integer caseId;
    private String translateId1;
    private String translateId2;
    private String translateId3;
    private String type;
    private String state;
    private String description;
    private Date createdAt;

    // 关联表字段
    private String serviceInfo;    // pl_service.service_info
    private String linkInfo;      // pl_link.link_info
    private String analysisName;  // pl_analysis.name
    private String caseName;      // pl_case.name (重命名)
    private String translateInfo1;// pl_translate.translate_info (对应translate_id1)
    private String translateInfo2;// pl_translate.translate_info (对应translate_id2)
    private String translateInfo3;// pl_translate.translate_info (对应translate_id3)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getAudioId() {
        return audioId;
    }

    public void setAudioId(Integer audioId) {
        this.audioId = audioId;
    }

    public String getBeginTxt() {
        return beginTxt;
    }

    public void setBeginTxt(String beginTxt) {
        this.beginTxt = beginTxt;
    }

    public String getEndTxt() {
        return endTxt;
    }

    public void setEndTxt(String endTxt) {
        this.endTxt = endTxt;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getTranslateId1() {
        return translateId1;
    }

    public void setTranslateId1(String translateId1) {
        this.translateId1 = translateId1;
    }

    public String getTranslateId2() {
        return translateId2;
    }

    public void setTranslateId2(String translateId2) {
        this.translateId2 = translateId2;
    }

    public String getTranslateId3() {
        return translateId3;
    }

    public void setTranslateId3(String translateId3) {
        this.translateId3 = translateId3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getTranslateInfo1() {
        return translateInfo1;
    }

    public void setTranslateInfo1(String translateInfo1) {
        this.translateInfo1 = translateInfo1;
    }

    public String getTranslateInfo2() {
        return translateInfo2;
    }

    public void setTranslateInfo2(String translateInfo2) {
        this.translateInfo2 = translateInfo2;
    }

    public String getTranslateInfo3() {
        return translateInfo3;
    }

    public void setTranslateInfo3(String translateInfo3) {
        this.translateInfo3 = translateInfo3;
    }
}