package allinter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.walk;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CaseTest {
    private Path workDirectory = null;

    @Before
    public void createWorkDirectory() throws IOException {
        this.workDirectory = createTempDirectory("testMain001");
    }

    @After
    public void destroyWorkDirectory() throws IOException {
        if (this.workDirectory == null) {
            return;
        }

        walk(this.workDirectory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        this.workDirectory = null;
    }

    @Test
    public void testCase20200808_0001() throws Exception {
        final Path htmlCheckerReport = this.workDirectory.resolve("a.json");
        final Path lowvisionReport = this.workDirectory.resolve("b.json");
        final Path outputImage = this.workDirectory.resolve("c.jpeg");
        final Path sourceImage = this.workDirectory.resolve("d.jpeg");

        final String[] args = new String[]{
                "--no-interactive",
                "--html-checker-output-report", htmlCheckerReport.toString(),
                "--lowvision-output-report", lowvisionReport.toString(),
                "--lowvision-output-image", outputImage.toString(),
                "--lowvision-source-image", sourceImage.toString(),
                "https://sunny4381.github.io/allinter/fixtures/case-20200808_0001.html"};
        assertThat(App.execute(args), is(0));
        assertThat(Files.exists(htmlCheckerReport), is(true));
        assertThat(Files.exists(lowvisionReport), is(true));
        assertThat(Files.exists(outputImage), is(true));
        assertThat(Files.exists(sourceImage), is(true));
    }
}
