package galen.galen_framework_test;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

public class LoginPageLayoutTest extends PageInit {
	
	private static final String LOGIN_PAGE_SPEC_PATH = "/specs/loginPageGdc.spec";
	private static final String LOGIN_PAGE_SPEC_ERROR_PATH = "/specs/loginPageGdcError.spec";
	
	@Test
	public void checkValidLayoutInDesktopMode(Method method) throws IOException, URISyntaxException {
		// Set size
		resize(Environment.DESKTOP.getScreenSize());
		
		// check layout
		checkLayout(LOGIN_PAGE_SPEC_PATH, singletonList(Environment.DESKTOP.toString()), method);
	}

	@Test
	public void checkValidLayoutInMobileMode(Method method) throws IOException, URISyntaxException {
		// Set size
		resize(Environment.MOBILE.getScreenSize());
		
		// check layout
		checkLayout(LOGIN_PAGE_SPEC_PATH, singletonList(Environment.MOBILE.toString()), method);
	}

	@Test
	public void checkInvalidLayoutInDesktopMode(Method method) throws IOException, URISyntaxException {
		// Set size
		resize(Environment.DESKTOP.getScreenSize());
		
		// check layout
		checkLayout(LOGIN_PAGE_SPEC_ERROR_PATH, singletonList(Environment.DESKTOP.toString()), method);
	}

	@Test
	public void checkInvalidLayoutInMobileMode(Method method) throws IOException, URISyntaxException {
		// Set size
		resize(Environment.MOBILE.getScreenSize());
		
		// check layout
		checkLayout(LOGIN_PAGE_SPEC_ERROR_PATH, singletonList(Environment.MOBILE.toString()), method);
	}
	
}
