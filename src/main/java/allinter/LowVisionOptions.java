package allinter;

import picocli.CommandLine;

public class LowVisionOptions {
    @CommandLine.Option(names = "--no-lowvision", negatable = true)
    private boolean lowvision = true;

    @CommandLine.Option(names = "--lowvision-output-report", description = "specifies output report file. default is a.json")
    private String outputReportFilepath = "a.json";

    @CommandLine.Option(names = "--lowvision-output-image", description = "specifies output image file. default doesn't output image file.")
    private String outputImageFilepath = null;

    @CommandLine.Option(names = "--lowvision-source-image", description = "specifies output source image file. default doesn't output source image file.")
    private String sourceImageFilepath = null;

    @CommandLine.Option(names = "--no-lowvision-eyesight", negatable = true)
    private boolean lowvisionEyesight = true;

    @CommandLine.Option(names = "--lowvision-eyesight-degree")
    private float lowvisionEyesightDegree = 0.5f;

    @CommandLine.Option(names = "--no-lowvision-cvd", negatable = true)
    private boolean lowvisionCVD = true;

    @CommandLine.Option(names = "--lowvision-cvd-type")
    private int lowvisionCVDType = 2;

    @CommandLine.Option(names = "--no-lowvision-color-filter", negatable = true)
    private boolean lowvisionColorFilter = true;

    @CommandLine.Option(names = "--lowvision-color-filter-degree")
    private float lowvisionColorFilterDegree = 0.8f;

    public LowVisionOptions() {
    }

    public boolean isLowvision() {
        return lowvision;
    }

    public void setLowvision(boolean lowvision) {
        this.lowvision = lowvision;
    }

    public String getOutputReportFilepath() {
        return outputReportFilepath;
    }

    public void setOutputReportFilepath(String outputReportFilepath) {
        this.outputReportFilepath = outputReportFilepath;
    }

    public String getOutputImageFilepath() {
        return outputImageFilepath;
    }

    public void setOutputImageFilepath(String outputImageFilepath) {
        this.outputImageFilepath = outputImageFilepath;
    }

    public String getSourceImageFilepath() {
        return sourceImageFilepath;
    }

    public void setSourceImageFilepath(String sourceImageFilepath) {
        this.sourceImageFilepath = sourceImageFilepath;
    }

    public boolean isLowvisionEyesight() {
        return lowvisionEyesight;
    }

    public void setLowvisionEyesight(boolean lowvisionEyesight) {
        this.lowvisionEyesight = lowvisionEyesight;
    }

    public float getLowvisionEyesightDegree() {
        return lowvisionEyesightDegree;
    }

    public void setLowvisionEyesightDegree(float lowvisionEyesightDegree) {
        this.lowvisionEyesightDegree = lowvisionEyesightDegree;
    }

    public boolean isLowvisionCVD() {
        return lowvisionCVD;
    }

    public void setLowvisionCVD(boolean lowvisionCVD) {
        this.lowvisionCVD = lowvisionCVD;
    }

    public int getLowvisionCVDType() {
        return lowvisionCVDType;
    }

    public void setLowvisionCVDType(int lowvisionCVDType) {
        this.lowvisionCVDType = lowvisionCVDType;
    }

    public boolean isLowvisionColorFilter() {
        return lowvisionColorFilter;
    }

    public void setLowvisionColorFilter(boolean lowvisionColorFilter) {
        this.lowvisionColorFilter = lowvisionColorFilter;
    }

    public float getLowvisionColorFilterDegree() {
        return lowvisionColorFilterDegree;
    }

    public void setLowvisionColorFilterDegree(float lowvisionColorFilterDegree) {
        this.lowvisionColorFilterDegree = lowvisionColorFilterDegree;
    }
}
