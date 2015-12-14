package galen.galen_framework_test;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.galenframework.api.Galen;
import com.galenframework.reports.TestReport;
import com.galenframework.reports.model.LayoutObject;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.reports.model.LayoutSection;
import com.galenframework.reports.model.LayoutSpec;
import com.galenframework.specs.reader.page.SectionFilter;
import com.galenframework.support.GalenReportsContainer;
import com.galenframework.testng.GalenTestNgReportsListener;

@Listeners(GalenTestNgReportsListener.class)
public class PageInit {
	
	protected static final String GOODDATA_URL = "https://staging2.intgdc.com";
	
	protected WebDriver driver;
	protected WebDriverWait wait;

	@BeforeClass(groups = "init")
	public WebDriver getDriver() {
		return driver = new FirefoxDriver();
	}

	@BeforeClass(dependsOnGroups = "init")
	public WebDriverWait getDriverWait() {
		return wait = new WebDriverWait(driver, 30);
	}

	@BeforeMethod
	public void prepareForTest() {
		driver.manage().deleteAllCookies();
		driver.get(GOODDATA_URL);
		wait.until(elementToBeClickable(By.className("s-btn-sign_in")));
	}
	
	@AfterClass(alwaysRun = true)
		public void tearDown() {
		driver.quit();
		}

	public void checkLayout(final String pSpecPath, List<String> includedTags, Method method) throws IOException, URISyntaxException {
		String title = "Check layout " + pSpecPath;
		TestReport test = GalenReportsContainer.get().registerTest(method);
		LayoutReport layoutReport = Galen.checkLayout(driver, pSpecPath, new SectionFilter(includedTags,null),
				new Properties(), null,null);
		layoutReport.setTitle(title);
		test.layout(layoutReport, title);

		if (layoutReport.errors() > 0) {
			final StringBuffer errorDetails = new StringBuffer();
			for (LayoutSection layoutSection : layoutReport.getSections()) {
				final StringBuffer layoutDetails = new StringBuffer();
				layoutDetails.append("\n").append("Layout Section: ")
						.append(layoutSection.getName()).append("\n");
				for (LayoutObject layoutObject : layoutSection.getObjects()) {
					boolean hasErrors = false;
					final StringBuffer errorElementDetails = new StringBuffer();
					errorElementDetails.append("  Element: ").append(
							layoutObject.getName());
					for (LayoutSpec layoutSpec : layoutObject.getSpecs()) {
						if (layoutSpec.getErrors() != null	&& layoutSpec.getErrors().size() > 0) {
							errorElementDetails.append(layoutSpec
									.getErrors().toString());
							hasErrors = true;
						}
					}
					if (hasErrors) {
						errorDetails.append("ViewPort Details: ")
								.append(includedTags).append("\n");
						errorDetails.append(layoutDetails);
						errorDetails.append(errorElementDetails).append("\n");
					}
				}
			}

			throw new RuntimeException(errorDetails.toString());
		}
	}

	public void resize(Dimension screenSize) {
		driver.manage().window().setSize(screenSize);
	}
	
	public enum Environment {
		DESKTOP(1024, 768),
		MOBILE(450, 800);

		private int width;
		private int height;

		private Environment(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public Dimension getScreenSize() {
			return new Dimension(width, height);
		}

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
}
