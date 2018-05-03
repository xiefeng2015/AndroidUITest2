import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.FileAssert;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class IndexTest {

    private AndroidDriver driver;


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
        // 设置main Activity，例如  .mainNmae.activity  记得带上点
        capabilities.setCapability("appActivity", "com.cai8.MainActivity");
        try {

            // 加载驱动,ip,填写相应的ip和端口  例如   http://172.16.11.120:4720
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void resultTest(){
        System.out.println("Result:" +driver.isAppInstalled("包名"));
//        Assert.assertEquals(driver.isAppInstalled("包名"),true,"安装失败");
    }

    @AfterClass
    public void tearDown() throws Exception{
        driver.quit();
    }
}
