package com.example.bloghome.member;

import com.example.bloghome.member.dto.JoinRequestDto;
import com.example.bloghome.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Transactional
@SpringBootTest
//@DataJpaTest // JPA -> 테스트할때 마킹해준다.
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    // validation 테스트
    private static Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("닉네임 3자리 미만")
    @Test
    void nicknameLessThenThreeLetter() {
        // 성공
        JoinRequestDto request1 = new JoinRequestDto("susi1", "1111", "1111", "user");

        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
                new JoinRequestDto("susi2", "1111", "1111", "zs"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
                new JoinRequestDto("susi3", "1111", "1111", "tr"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호가 4자 미만")
    @Test
    void passwordLessThenFourLetter() {
        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
                new JoinRequestDto("susi4", "123", "123", "user"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
                new JoinRequestDto("susi5", "234", "234", "user"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호에 닉네임이 포함")
    @Test
    void containNickname() {
// 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user1");
// 비밀번호에 닉네임이 포함된 경우
        JoinRequestDto request6 = new JoinRequestDto("susi6", "user212", "user12", "user2");
        JoinRequestDto request7 = new JoinRequestDto("susi7", "user312", "user12", "user3");

        // Exception Test
        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request6));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request7));
    }

    @DisplayName("비밀번호가 다른경우")
    @Test
    void wrongPasswordCheckPassword() {
// 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");

// 비밀번호가 다름
        JoinRequestDto request8 = new JoinRequestDto("user8", "1111", "1234", "user");
        JoinRequestDto request9 = new JoinRequestDto("user9", "1111", "1112", "user");

        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));

// Exception Test
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request8));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request9));

    }
}