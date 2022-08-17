import com.solo.APIpractice.member.Member;
import com.solo.APIpractice.member.MemberController;
import com.solo.APIpractice.member.MemberRepository;
import com.solo.APIpractice.member.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static com.solo.APIpractice.restdocs.ApiDocumentUtil.getRequestPreProcessor;
import static com.solo.APIpractice.restdocs.ApiDocumentUtil.getResponsePreProcessor;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberController memberController;

    @Mock
    private MemberRepository memberRepository;

       @Test
    public void getMembersTest() throws Exception {
        //given
        List<Member> members = Arrays.asList(
                new Member(1L, "홍길동1",'M', "코드스테이츠", 5L, 1L),
                new Member(2L, "홍길동2",'F', "유어클래스", 4L, 2L)
        );
        when(memberController.getMembers()).thenReturn(members);

        //when, then
       mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
               .andDo(document("get-members",
                       getRequestPreProcessor(),
                       getResponsePreProcessor(),
                       responseFields(
                               List.of(
                                       fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                       fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
                                       fieldWithPath("[].sex").type(JsonFieldType.STRING).description("성별"),
                                       fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                       fieldWithPath("[].companyType").type(JsonFieldType.NUMBER).description("업종"),
                                       fieldWithPath("[].companyLocation").type(JsonFieldType.NUMBER).description("지역")

                               )
                       )));
    }

    @Test
    public void getMembersByLocationTest() throws Exception {
        //given
        long companyLocation = 1L;
        List<Member> members = Arrays.asList(
                new Member(1L, "홍길동1",'M', "코드스테이츠", 5L, 1L),
                new Member(2L, "홍길동2",'F', "유어클래스", 4L, 2L),
                new Member(3L, "홍길동3",'M', "유어클래스2", 4L, 1L)
        );

        when(memberController.getMembersLocation(Mockito.anyLong())).thenReturn(members);

        mockMvc.perform(get("/members/{companyLocation}", companyLocation))
                .andExpect(status().isOk())
                .andDo(document("get-members-location",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("companyLocation").description("지역 식별자 ID")),
                                responseFields(
                                        List.of(
                                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
                                                fieldWithPath("[].sex").type(JsonFieldType.STRING).description("성별"),
                                                fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                                fieldWithPath("[].companyType").type(JsonFieldType.NUMBER).description("업종"),
                                                fieldWithPath("[].companyLocation").type(JsonFieldType.NUMBER).description("지역")

                                        )
                        )));
    }

    @Test
    public void getMembersByTypeTest() throws Exception {
        //given
        long companyType = 5L;
        List<Member> members = Arrays.asList(
                new Member(1L, "홍길동1", 'M', "코드스테이츠", 5L, 1L),
                new Member(2L, "홍길동2", 'F', "유어클래스", 4L, 2L),
                new Member(3L, "홍길동3", 'M', "유어클래스2", 4L, 1L)
        );

        when(memberController.getMembersType(Mockito.anyLong())).thenReturn(members);
        mockMvc.perform(get("/members/{companyType}", companyType))
                .andExpect(status().isOk())
                .andDo(document("get-members-Type",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("companyType").description("업종 식별자 ID")),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("[].sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("[].companyType").type(JsonFieldType.NUMBER).description("업종"),
                                        fieldWithPath("[].companyLocation").type(JsonFieldType.NUMBER).description("지역")

                                )
                        )));
    }
}
