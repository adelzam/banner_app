package com.test_app.banner_app;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.service.BannerService;
import com.test_app.banner_app.service.LocalService;
import com.test_app.banner_app.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create_user_before.sql", "/create_local_before.sql", "/create_banners_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_banners_before.sql", "/create_local_after.sql", "/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BannerJunitTest {

    @Autowired
    BannerService bannerService;

    @Autowired
    LocalService localService;

    @Autowired
    UserService userService;

    @Before
    public void addTestBannerToDB() {
        Local local = localService.getLocalById(1001);
        User user = userService.getUserByUserName("test");
        Banner banner = new Banner(
                local,
                "http://google.com",
                400,
                100,
                "/local/usr/main/img1.jpg");
        bannerService.createOrUpdateBanner(user, banner, 1001);
    }

    @Test
    public void testAddBanner() throws Exception {
        Banner banner = new Banner(
                localService.getLocalById(1001),
                "http://google.com",
                400,
                100,
                "/local/usr/main/img1.jpg");
        assertEquals(banner.toString(), bannerService.getBanner(1010).toString());
    }

    @Test
    public void testDeleteBanner() throws Exception {
        bannerService.deletedBanner(userService.getUserByUserName("test"), 1010);
        assertNull(bannerService.getBanner(1010));
    }

    @Test
    public void testUpdateBanner() throws Exception {
        Banner banner = bannerService.getBanner(1010);
        banner.setWidth(600);
        banner.setHeight(40);
        banner.setId(1010);
        bannerService.createOrUpdateBanner(userService.getUserByUserName("test"), banner, 1001);
        assertEquals(banner.toString(), bannerService.getBanner(1010).toString());
    }
}
