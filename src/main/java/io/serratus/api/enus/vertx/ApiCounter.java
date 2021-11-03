package io.serratus.api.enus.vertx;


/**
 * Keyword: classSimpleNameApiCounter
 * Map.hackathonMission: to create a new Java class to count the number of API requests are currently being made at the same time for fine tuning memory performance for bulk operations. 
 * Map.hackathonColumn: Develop Base Classes
 * Map.hackathonLabels: Java
 **/
public class ApiCounter {
    private Long totalNum;
    private Long queueNum;
    private Long totalNumOld;
	private Long queueNumOld;


    public Long getTotalNum() {
        return totalNum;
    }

    public void incrementTotalNum(Long num) {
        this.totalNum+=num;
    }

    public void incrementTotalNum() {
        this.totalNum++;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getQueueNum() {
        return queueNum;
    }

    public void incrementQueueNum(Long num) {
        this.queueNum+=num;
    }

    public void incrementQueueNum() {
        this.queueNum++;
    }
    public void decrementQueueNum() {
        this.queueNum--;
    }
    public void setQueueNum(Long countNum) {
        this.queueNum = countNum;
    }

    public Long getTotalNumOld() {
        return totalNumOld;
    }

    public void setTotalNumOld(Long totalNumOld) {
        this.totalNumOld = totalNumOld;
    }

    public Long getQueueNumOld() {
        return queueNumOld;
    }

    public void setQueueNumOld(Long countNumOld) {
        this.queueNumOld = countNumOld;
    }
}