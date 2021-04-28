package org.zhezlov.documentarchive.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.zhezlov.documentarchive.service.SecurityServiceImpl;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration({
        "classpath:appconfig-root.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    SecurityServiceImpl securityServiceImpl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    public void welcome() throws Exception {
        securityServiceImpl.autoLogin("andro1d1", "12345678");
        perform(get("/welcome"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(forwardedUrl("/WEB-INF/views/welcome.jsp"))
                .andExpect(model().attribute("documents", Matchers.hasSize(4)))
                .andExpect(model().attribute("documents", Matchers.hasItem(
                        Matchers.allOf(
                                Matchers.hasProperty("id", Matchers.is(1L)),
                                Matchers.hasProperty("name", Matchers.is("Жезлов Андрей (1).pdf"))
                        )
                )));
    }

    @Test
    public void create() throws Exception {
        securityServiceImpl.autoLogin("andro1d1", "12345678");
        perform(get("/documents/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("documentForm"))
                .andExpect(forwardedUrl("/WEB-INF/views/documentForm.jsp"))
                .andExpect(model().attribute("document", Matchers.notNullValue()));
    }
}