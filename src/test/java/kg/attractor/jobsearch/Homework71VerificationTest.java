package kg.attractor.jobsearch;

import freemarker.template.Configuration;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.model.*;
import kg.attractor.jobsearch.repository.*;
import kg.attractor.jobsearch.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:homework71http;DB_CLOSE_DELAY=-1",
        "jobsearch.recovery.demo=true"
})
class Homework71VerificationTest {
    @Value("${local.server.port}") int port;
    @Autowired UserRepository users;
    @Autowired VacancyRepository vacancies;
    @Autowired ResumeRepository resumes;
    @Autowired CategoryRepository categories;
    @Autowired MessageRepository messages;
    @Autowired RespondedApplicantRepository responses;
    @Autowired UserService userService;
    @Autowired PasswordRecoveryService recovery;
    @Autowired PasswordEncoder encoder;

    class Browser {
        HttpClient http = HttpClient.newBuilder().cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL)).build();
        HttpResponse<String> get(String path) throws Exception {
            return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build(), HttpResponse.BodyHandlers.ofString());
        }
        HttpResponse<String> post(String path, Map<String,String> fields, String token) throws Exception {
            Map<String,String> values = new LinkedHashMap<>(fields);
            if (token != null) values.put("_csrf", token);
            String body = values.entrySet().stream().map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)).reduce((a,b) -> a + "&" + b).orElse("");
            return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).header("Content-Type","application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
        }
        String token(String path) throws Exception {
            var page = get(path);
            assertEquals(200,page.statusCode(),path);
            var matcher = Pattern.compile("name=\"_csrf\"\\s+value=\"([^\"]+)\"").matcher(page.body());
            assertTrue(matcher.find(),"CSRF missing: " + path);
            return matcher.group(1);
        }
        void login(String email, String password) throws Exception {
            var reply=post("/auth/login",Map.of("username",email,"password",password),token("/auth/login"));
            assertEquals(302,reply.statusCode());
            assertFalse(reply.headers().firstValue("location").orElse("").contains("error"),"Login failed");
        }
    }

    @Test
    void parseEveryTemplate() throws Exception {
        Path directory=Path.of("src/main/resources/templates");
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_34);
        cfg.setDirectoryForTemplateLoading(directory.toFile());
        try(var paths=Files.walk(directory)) {
            for(Path p:paths.filter(p -> p.toString().endsWith(".ftlh")).toList()) {
                cfg.getTemplate(directory.relativize(p).toString().replace('\\','/'));
            }
        }
    }

    @Test
    void completeWebFlows() throws Exception {
        User applicant=users.findByEmailIgnoreCase("applicant@attractor.com").orElseThrow();
        User employer=users.findByEmailIgnoreCase("employer@attractor.com").orElseThrow();
        for(User user:List.of(applicant,employer)) { user.setPassword(encoder.encode("Password71")); users.save(user); }
        UserCreateDto otherDto=new UserCreateDto();
        otherDto.setName("Other"); otherDto.setSurname("Tester"); otherDto.setAge(25);
        otherDto.setEmail("outsider71@example.com"); otherDto.setPhoneNumber("+996700999888");
        otherDto.setPassword("Password71"); otherDto.setAccountType(AccountType.APPLICANT);
        userService.register(otherDto);
        var category=categories.findAll().getFirst();
        for(int i=0;i<25;i++) {
            Vacancy v=new Vacancy(); v.setName("VerificationVacancy"+i); v.setDescription("Verification description");
            v.setCategory(category); v.setAuthor(employer); v.setSalary(BigDecimal.valueOf(1000+i));
            v.setExpFrom(0);v.setExpTo(10);v.setIsActive(true);
            v.setCreatedDate(LocalDateTime.now().minusDays(i));v.setUpdateTime(LocalDateTime.now().minusDays(i));
            vacancies.save(v);
        }
        Browser guest=new Browser();
        for(String language:List.of("ru","en")) {
            for(String path:List.of("/auth/login","/auth/register","/auth/forgot_password","/auth/reset_password","/vacancies")) {
                assertEquals(200,guest.get(path+"?lang="+language).statusCode(),path+" "+language);
            }
        }
        for(String sort:List.of("dateDesc","dateAsc","salaryAsc","salaryDesc","responsesDesc","responsesAsc")) {
            var result=guest.get("/vacancies/filter?name=VerificationVacancy&salary=1000.01&experience=2&sort="+sort);
            assertEquals(200,result.statusCode(),sort);
            assertEquals(20,result.body().split("<article",-1).length-1,sort);
            assertTrue(result.body().contains("salary=1000.01"));
        }
        var page2=guest.get("/vacancies/filter?name=VerificationVacancy&page=2");
        assertEquals(200,page2.statusCode());
        assertEquals(5,page2.body().split("<article",-1).length-1);
        assertTrue(guest.get("/vacancies/filter?name=NoMatchingName71").body().contains("No matching"));
        assertEquals(200,guest.get("/vacancies?salary=bad").statusCode());
        assertEquals(200,guest.get("/vacancies?category=bad").statusCode());
        assertNotEquals(200,guest.get("/responses").statusCode());

        Browser a=new Browser(); a.login(applicant.getEmail(),"Password71");
        Browser e=new Browser(); e.login(employer.getEmail(),"Password71");
        Browser o=new Browser(); o.login(otherDto.getEmail(),"Password71");
        var resume=resumes.findByApplicant_IdAndIsActiveTrueOrderByUpdateTimeDesc(applicant.getId()).getFirst();
        var vacancy=vacancies.findByAuthor_IdAndIsActiveTrueOrderByUpdateTimeDesc(employer.getId()).getFirst();
        for(String path:List.of("/profile","/profile/edit","/resumes/form/create","/resumes/form/edit/"+resume.getId(),"/vacancies/"+vacancy.getId(),"/companies","/companies/"+employer.getId())) {
            assertEquals(200,a.get(path).statusCode(),path);
        }
        for(String path:List.of("/profile","/resumes","/resumes?category="+category.getId(),"/resumes/"+resume.getId(),"/vacancies/form/create","/vacancies/form/edit/"+vacancy.getId(),"/responses")) {
            assertEquals(200,e.get(path).statusCode(),path);
        }
        assertTrue(a.get("/vacancies/"+vacancy.getId()).body().contains("response-modal"));
        assertEquals(200,e.get("/applicants/"+applicant.getId()).statusCode());
        assertEquals(403,a.get("/applicants/"+applicant.getId()).statusCode());
        for(String sort:List.of("dateAsc","responsesAsc","responsesDesc")) {
            assertEquals(200,e.get("/resumes?sort="+sort+"&category="+category.getId()).statusCode());
        }
        assertTrue(e.get("/resumes/"+resume.getId()).body().contains("invite-modal"));
        var fields=Map.of("resumeId",resume.getId().toString(),"vacancyId",vacancy.getId().toString());
        assertEquals(403,o.post("/responses/open",fields,o.token("/profile")).statusCode());
        assertEquals(403,a.post("/responses/open",fields,null).statusCode());
        var opened=a.post("/responses/open",fields,a.token("/profile"));
        assertEquals(302,opened.statusCode());
        String chat=URI.create(opened.headers().firstValue("location").orElseThrow()).getPath();
        assertEquals(chat,URI.create(e.post("/responses/open",fields,e.token("/profile")).headers().firstValue("location").orElseThrow()).getPath());
        assertEquals(200,a.get("/responses").statusCode());
        assertEquals(200,e.get("/responses?vacancyId="+vacancy.getId()).statusCode());
        assertEquals(403,o.get(chat).statusCode());
        long count=messages.count();
        assertEquals(403,o.post(chat,Map.of("content","unauthorized"),o.token("/profile")).statusCode());
        assertEquals(count,messages.count());
        assertEquals(200,a.post(chat,Map.of("content","   "),a.token(chat)).statusCode());
        assertEquals(count,messages.count());
        assertEquals(302,a.post(chat,Map.of("content","Hello <script>alert(1)</script>"),a.token(chat)).statusCode());
        assertTrue(e.get(chat).body().contains("&lt;script&gt;"));
        assertEquals(302,e.post(chat,Map.of("content","Employer reply"),e.token(chat)).statusCode());
        assertTrue(a.get(chat).body().contains("Employer reply"));

        var forgot=guest.post("/auth/forgot_password",Map.of("email",applicant.getEmail()),guest.token("/auth/forgot_password"));
        assertEquals(200,forgot.statusCode());
        var tokenMatch=Pattern.compile("id=\"demo-token\">([^<]+)").matcher(forgot.body());
        assertTrue(tokenMatch.find()); String token=tokenMatch.group(1);
        assertTrue(recovery.valid(token));
        String resetPath="/auth/reset_password?token="+token;
        assertEquals(200,guest.post("/auth/reset_password",Map.of("token",token,"password","weak"),guest.token(resetPath)).statusCode());
        assertTrue(recovery.valid(token));
        assertEquals(200,guest.post("/auth/reset_password",Map.of("token",token,"password","NewPassword71"),guest.token(resetPath)).statusCode());
        assertFalse(recovery.valid(token));
        assertFalse(recovery.reset(token,"OtherPassword71"));
        Browser after=new Browser(); after.login(applicant.getEmail(),"NewPassword71");
        var oldLogin=new Browser();
        assertTrue(oldLogin.post("/auth/login",Map.of("username",applicant.getEmail(),"password","Password71"),oldLogin.token("/auth/login")).headers().firstValue("location").orElseThrow().contains("error"));
        String first=recovery.issue(applicant.getEmail());String second=recovery.issue(applicant.getEmail());
        assertFalse(recovery.valid(first));assertTrue(recovery.valid(second));
        var expiring=users.findByEmailIgnoreCase(applicant.getEmail()).orElseThrow();
        expiring.setResetPasswordExpiresAt(LocalDateTime.now().minusMinutes(1));users.save(expiring);
        assertFalse(recovery.reset(second,"ExpiredPassword71"));
        assertEquals(200,guest.get("/auth/reset_password?token=wrong").statusCode());
    }
}
