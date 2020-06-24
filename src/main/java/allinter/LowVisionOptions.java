package allinter;

import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LowVisionOptions {
    @CommandLine.Option(names = "--no-lowvision", negatable = true)
    private boolean lowvision = true;

    @CommandLine.Option(names = "--lowvision-output-report", description = "specifies output report file. default is lv-report.json")
    private Path outputReportFilepath = Paths.get("lv-report.json");

    @CommandLine.Option(names = "--lowvision-output-image", description = "specifies output image file. default doesn't output image file.")
    private Path outputImageFilepath = null;

    @CommandLine.Option(names = "--lowvision-source-image", description = "specifies output source image file. default doesn't output source image file.")
    private Path sourceImageFilepath = null;

    @CommandLine.Option(names = "--no-lowvision-eyesight", negatable = true)
    private boolean eyesight = true;

    @CommandLine.Option(names = "--lowvision-eyesight-degree")
    private float eyesightDegree = 0.5f;

    @CommandLine.Option(names = "--no-lowvision-cvd", negatable = true)
    private boolean cvd = true;

    @CommandLine.Option(names = "--lowvision-cvd-type")
    private int cvdType = 2;

    @CommandLine.Option(names = "--no-lowvision-color-filter", negatable = true)
    private boolean colorFilter = true;

    @CommandLine.Option(names = "--lowvision-color-filter-degree")
    private float colorFilterDegree = 0.8f;

    public LowVisionOptions() {
    }

    public boolean isLowvision() {
        return lowvision;
    }

    public void setLowvision(boolean lowvision) {
        this.lowvision = lowvision;
    }

    public Path getOutputReportFilepath() {
        return outputReportFilepath;
    }

    public void setOutputReportFilepath(Path outputReportFilepath) {
        this.outputReportFilepath = outputReportFilepath;
    }

    public Path getOutputImageFilepath() {
        return outputImageFilepath;
    }

    public void setOutputImageFilepath(Path outputImageFilepath) {
        this.outputImageFilepath = outputImageFilepath;
    }

    public Path getSourceImageFilepath() {
        return sourceImageFilepath;
    }

    public void setSourceImageFilepath(Path sourceImageFilepath) {
        this.sourceImageFilepath = sourceImageFilepath;
    }

    public boolean isEyesight() {
        return eyesight;
    }

    public void setEyesight(boolean eyesight) {
        this.eyesight = eyesight;
    }

    public float getEyesightDegree() {
        return eyesightDegree;
    }

    public void setEyesightDegree(float eyesightDegree) {
        this.eyesightDegree = eyesightDegree;
    }

    public boolean isCvd() {
        return cvd;
    }

    public void setCvd(boolean cvd) {
        this.cvd = cvd;
    }

    public int getCvdType() {
        return cvdType;
    }

    public void setCvdType(int cvdType) {
        this.cvdType = cvdType;
    }

    public boolean isColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(boolean colorFilter) {
        this.colorFilter = colorFilter;
    }

    public float getColorFilterDegree() {
        return colorFilterDegree;
    }

    public void setColorFilterDegree(float colorFilterDegree) {
        this.colorFilterDegree = colorFilterDegree;
    }

    public String getCvdTypeName() {
        switch(cvdType) {
        case 1:
            return "protan";
        case 3:
            return "tritan";
        default:
            return "deutan";
        }
    }

    public void setCvdTypeByName(final String cvdType) {
        if ("protan".equalsIgnoreCase(cvdType)) {
            this.setCvdType(1);
        } else if ("deutan".equalsIgnoreCase(cvdType)) {
            this.setCvdType(2);
        } else if ("tritan".equalsIgnoreCase(cvdType)) {
            this.setCvdType(3);
        }
    }
}
