import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.FileAssert;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Hiwan8UITest {

    private AndroidDriver driver;
    DBTest dbTest = new DBTest();


    //初始化参数，安装apk
    @BeforeClass
    public void setUp() throws Exception {

        //设置启动的程序位置和程序的名字，安装的apk文件
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "./apps");
        File app = new File(appDir, "hiwan.apk");

        // 设置设备的属性
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        // 设置平台 Android
        capabilities.setCapability("platformName", "Android");
        // 设置设备的名称，真机或者模拟器的, 设备连接电脑，在命令行输入adb  devices  查看即可
        capabilities.setCapability("deviceName", "Nexus6.0");
        // 设置Android系统的版本号，例如 4.3  4.4
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("app", app.getAbsolutePath());
        // 设置apk的包名
        capabilities.setCapability("appPackage", "com.wesai.hiwan");
        // 设置main Activity
        capabilities.setCapability("appActivity", "com.wesai.hiwan.MainActivity");
        try {

            // 加载驱动,ip,填写相应的ip和端口  例如   http://172.16.11.120:4720
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "检查APP是否安装成功")
    public void isInstall(){
       Assert.assertTrue(driver.isAppInstalled("com.wesai.hiwan"));
    }


    @Test(description = "手机号密码用户登录")
    public void LoginTest() throws InterruptedException {
       Thread.sleep(5000);
       driver.findElementById("com.wesai.hiwan:id/mine_text").click();
       driver.findElementById("com.wesai.hiwan:id/edit_phone_number").sendKeys("15882066239");
       driver.findElementById("com.wesai.hiwan:id/edit_pass_word").sendKeys("lucky20180101");
       driver.findElementById("com.wesai.hiwan:id/btn_login").click();
       Thread.sleep(3000);
       driver.findElementById("com.wesai.hiwan:id/mine_text").click();
       Assert.assertEquals("鹿鹿",driver.findElementById("com.wesai.hiwan:id/nickName").getText());
    }

    @Test(description = "测试我的页面数据")
    public void MyPageTest() throws InterruptedException {
        driver.findElementById("com.wesai.hiwan:id/mine_text").click();
        Assert.assertNotNull(driver.findElementById("com.wesai.hiwan:id/mine_text_tips").getText(),"卡包数和认证状态不为空");
        Assert.assertEquals(driver.findElementByXPath("//android.widget.TextView[@" +
                "resource-id='com.wesai.hiwan:id/mine_text_tips'][1]").getText(),"868张");
        Assert.assertEquals(driver.findElementByXPath("//android.widget.LinearLayout[5]" +
                "/android.widget.RelativeLayout/android.widget.TextView[contains(@index,2)]").getText(),"已通过");

    }

    @Test(description = "退出登录成功后回到登录页")
    public void logoutTest() throws InterruptedException {
//        //进入我的卡包页面
//        driver.swipe(285,1080,285,1400,1);
//        Thread.sleep(7000);
//        //点击返回
//        driver.sendKeyEvent(4);
//        Thread.sleep(3000);
        //我的页面上滑
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width / 2 , height * 3 / 4, width / 2,height / 4,3000);
        Thread.sleep(5000);
        //进入设置页
        driver.findElementByXPath("//android.support.v7.widget.RecyclerView/android." +
              "widget.LinearLayout[8]/android.widget.RelativeLayout/android.widget.TextView").click();
        Thread.sleep(5000);
        //点击退出登录
        driver.findElementById("com.wesai.hiwan:id/hxsetting_btn").click();
        Thread.sleep(3000);
        //断言退出登录后是否回到登录页
        Assert.assertEquals(driver.findElementById("com.wesai.hiwan:id/titlebar_tv").getText(),"登录");
    }

    @Test(description = "手机号验证码登录")
    public void verCode_Login() throws InterruptedException, SQLException, ClassNotFoundException {
//        Thread.sleep(5000);
//        driver.findElementById("com.wesai.hiwan:id/mine_text").click();
        //点击登录按钮
        driver.findElementById("com.wesai.hiwan:id/titlebar_tv_right").click();
        Thread.sleep(5000);
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        driver.swipe(width * 3 / 4 , height / 2, width / 10,height / 2,3000);
        Thread.sleep(3000);
        driver.findElementById("com.wesai.hiwan:id/edit_phone_number").sendKeys("15882066239");
        driver.findElementById("com.wesai.hiwan:id/tv_get_ver").click();
        String ver_code = dbTest.sqlquery();
        Thread.sleep(2000);
        driver.findElementById("com.wesai.hiwan:id/edit_ver_code").sendKeys(ver_code);
        driver.findElementById("com.wesai.hiwan:id/btn_ver_login").click();
        Thread.sleep(3000);
        driver.findElementById("com.wesai.hiwan:id/mine_text").click();
        Assert.assertEquals("鹿鹿",driver.findElementById("com.wesai.hiwan:id/nickName").getText());

    }

    @Test(description = "注册")
    public void signin() throws SQLException, ClassNotFoundException, InterruptedException {
        driver.findElementById("com.wesai.hiwan:id/titlebar_tv_right").click();
        driver.findElementById("com.wesai.hiwan:id/edit_phone_number").sendKeys("15882066239");
        driver.findElementById("com.wesai.hiwan:id/tv_get_ver").click();
        String ver_code = dbTest.sqlquery();
        System.out.println(ver_code);
        driver.findElementById("com.wesai.hiwan:id/edit_ver_code").sendKeys(ver_code);
        Thread.sleep(3000);
        driver.findElementById("com.wesai.hiwan:id/edit_password").sendKeys("123456");
        driver.findElementById("com.wesai.hiwan:id/btn_register").click();
        Assert.assertEquals(driver.findElementById("com.wesai.hiwan:id/titlebar_tv").getText(),"登录");
    }

    @AfterClass

    public void tearDown() throws Exception{
        driver.quit();
    }
}
