package entities;

public class Report {

    int topPerformersThreshold;
    boolean useExperienceMultiplier;
    int periodLimit;

    public Report() {
    }

    public int getTopPerformersThreshold() {
        return topPerformersThreshold;
    }

    public void setTopPerformersThreshold(int topPerformersThreshold) {
        this.topPerformersThreshold = topPerformersThreshold;
    }

    public boolean isUseExperienceMultiplier() {
        return useExperienceMultiplier;
    }

    public void setUseExperienceMultiplier(boolean useExperienceMultiplier) {
        this.useExperienceMultiplier = useExperienceMultiplier;
    }

    public int getPeriodLimit() {
        return periodLimit;
    }

    public void setPeriodLimit(int periodLimit) {
        this.periodLimit = periodLimit;
    }
}
