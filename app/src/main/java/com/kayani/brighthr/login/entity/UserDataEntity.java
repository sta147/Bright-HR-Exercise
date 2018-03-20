package com.kayani.brighthr.login.entity;

public class UserDataEntity {
    private int companyId;
    private int userId;
    private String companyTimeZoneName;
    private String userTimeZoneName;
    private TokenEntity token;
    private boolean hasFixedWorkingTimePattern;
    private boolean hasEmploymentContract;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompanyTimeZoneName() {
        return companyTimeZoneName;
    }

    public void setCompanyTimeZoneName(String companyTimeZoneName) {
        this.companyTimeZoneName = companyTimeZoneName;
    }

    public String getUserTimeZoneName() {
        return userTimeZoneName;
    }

    public void setUserTimeZoneName(String userTimeZoneName) {
        this.userTimeZoneName = userTimeZoneName;
    }

    public TokenEntity getToken() {
        return token;
    }

    public void setToken(TokenEntity token) {
        this.token = token;
    }

    public boolean isHasFixedWorkingTimePattern() {
        return hasFixedWorkingTimePattern;
    }

    public void setHasFixedWorkingTimePattern(boolean hasFixedWorkingTimePattern) {
        this.hasFixedWorkingTimePattern = hasFixedWorkingTimePattern;
    }

    public boolean isHasEmploymentContract() {
        return hasEmploymentContract;
    }

    public void setHasEmploymentContract(boolean hasEmploymentContract) {
        this.hasEmploymentContract = hasEmploymentContract;
    }
}
