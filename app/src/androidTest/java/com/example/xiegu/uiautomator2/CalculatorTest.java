package com.example.xiegu.uiautomator2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    private final String BASIC_SAMPLE_PACKAGE = "com.android.calculator2";

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }
    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void calculatorTest() {
        mDevice.findObject(By.desc("Apps list")).click();
        mDevice.wait(Until.hasObject(By.desc("Calculator")), LAUNCH_TIMEOUT);
        mDevice.findObject(By.desc("Calculator")).click();

        UiObject2 button7 = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "digit_7")), 500);
        UiObject2 buttonX = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "op_mul")), 500);
        UiObject2 button6 = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "digit_6")), 500);
        UiObject2 buttonEqual = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "eq")), 500);
        UiObject2 output = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "result")), 500);

        button7.click();
        buttonX.click();
        button6.click();
        buttonEqual.click();
        assertEquals(output.getText(), "42");

    }

    @Test
    public void testCalculator(){
        startAPP(BASIC_SAMPLE_PACKAGE);
        mDevice.waitForWindowUpdate(BASIC_SAMPLE_PACKAGE, 5 * 2000);

        UiObject2 button7 = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "digit_7")), 5000);
        UiObject2 buttonX = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "op_mul")), 5000);
        UiObject2 button6 = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "digit_6")), 500);
        UiObject2 buttonEqual = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "eq")), 500);
        UiObject2 output = mDevice.wait(Until.findObject(By.res("com.android.calculator2", "result")), 500);

        button7.click();
        buttonX.click();
        button6.click();
        buttonEqual.click();
        assertEquals(output.getText(), "42");

        closeAPP(mDevice, BASIC_SAMPLE_PACKAGE);
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    private void startAPP(String sPackageName){
        Context mContext = InstrumentationRegistry.getContext();

        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(sPackageName);  //通过Intent启动app
        mContext.startActivity(myIntent);
    }

    private void closeAPP(UiDevice uiDevice,String sPackageName){
        Log.i(TAG, "closeAPP: ");
        try {
            uiDevice.executeShellCommand("am force-stop "+sPackageName);//通过命令行关闭app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAPP(UiDevice uiDevice,String sPackageName, String sLaunchActivity){
        try {
            uiDevice.executeShellCommand("am start -n "+sPackageName+"/"+sLaunchActivity);//通过命令行启动app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
