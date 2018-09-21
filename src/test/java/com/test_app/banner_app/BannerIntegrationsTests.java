package com.test_app.banner_app;

import com.test_app.banner_app.controllers.BannerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("test")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create_user_before.sql", "/create_banners_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_banners_after.sql", "/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BannerIntegrationsTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BannerController bannerController;

    @Test
    public void testMainPage() throws Exception {
        this.mockMvc.perform(get("/banners"))
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarNav']/div").string("test"));
    }

    @Test
    public void bannerListTest() throws Exception {
        this.mockMvc.perform(get("/banners"))
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr").nodeCount(4));
    }

    @Test
    public void updateBannerTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/banners/update")
                .param("id", "1001")
                .param("langId", "1002")
                .param("targetUrl", "yandex.com")
                .param("height", "100")
                .param("width", "100")
                .param("imgSrc", "image.jpg")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated());

        this.mockMvc.perform(get("/banners"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr[@id='row-1001']").exists())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr").nodeCount(4))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='country-1001']").string("Switzerland"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='lang-1001']").string("German"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='target-1001']").string("yandex.com"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='height-1001']").string("100"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='width-1001']").string("100"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='imgSrc-1001']").string("image.jpg"));

        this.mockMvc.perform(get("/audit/banner/1001"))
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr").nodeCount(2))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr[@id='row-1010']").exists())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerCountry-1001']").string("Switzerland"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerLang-1001']").string("German"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerTarget-1001']").string("yandex.com"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerHeight-1001']").string("100"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerWidth-1001']").string("100"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerImg-1001']").string("image.jpg"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='type-1010']/span").string("UPDATED"));
    }

    @Test
    public void checkGroupingFilter() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/banners/group-local")
                .param("langId", "1003")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr").nodeCount(2))
                .andExpect(xpath("//*[@id='banners_table']/thead/tr/th[9]/form/button").exists());
    }
    @Test
    public void addNewBannerTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/banners")
                .param("langId", "1001")
                .param("targetUrl", "yandex.ru")
                .param("height", "150")
                .param("width", "150")
                .param("imgSrc", "image.src")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr").nodeCount(5))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr[@id='row-1010']").exists())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='country-1010']").string("Russia"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='lang-1010']").string("Russian"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='target-1010']").string("yandex.ru"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='height-1010']").string("150"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='width-1010']").string("150"))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr/td[@id='imgSrc-1010']").string("image.src"));

        this.mockMvc.perform(get("/audit/banner/1010"))
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr").nodeCount(1))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr[@id='row-1011']").exists())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerCountry-1010']").string("Russia"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerLang-1010']").string("Russian"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerTarget-1010']").string("yandex.ru"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerHeight-1010']").string("150"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerWidth-1010']").string("150"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='bannerImg-1010']").string("image.src"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='type-1011']/span").string("CREATED"));
    }

    @Test
    public void deleteBannerTest() throws Exception {
        this.mockMvc.perform(get("/banners/delete/1001"))
                .andExpect(authenticated());

        this.mockMvc.perform(get("/banners"))
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr").nodeCount(3))
                .andExpect(xpath("//*[@id='banners_table']/tbody/tr[@id='row-1001']").doesNotExist());

        this.mockMvc.perform(get("/audit"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr[@id='row-1010']").exists())
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='banner_deleted-1010']")
                        .string("Banner was deleted. See last version in DELETED comment"))
                .andExpect(xpath("//*[@id='audit_table']/tbody/tr/td[@id='type-1010']/span").string("DELETED"));
    }
}