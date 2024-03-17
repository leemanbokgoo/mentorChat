package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentorTime.MentorTime;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.MentoringService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.mento.*;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;
    private final MentorService mentorService;
    private final MentoringService mentoringService;

    @GetMapping("/")
    public String main(@PageableDefault(page = 1) Pageable pageable, Model model, @LoginUser SessionUser sessionUser,
                @RequestParam(value = "filter", required = false) String filter, @RequestParam(value = "search", required = false) String search ) {

        if ( sessionUser != null  ){
            model.addAttribute("userName", sessionUser.getName());
        }

        // 멘토리스트 조회
        Page<MentorListResponseDto> mentorListPageDot = mentorService.findAllMentor(pageable, filter, search);

        int blockLimit = 5; // page 개수 설정
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), mentorListPageDot.getTotalPages());

        model.addAttribute("data", mentorListPageDot.getContent());
        model.addAttribute("dataPages", mentorListPageDot);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

    // 로그인한 유저 멘토 설정 조회
    @GetMapping("/mentor/save")
    public String mentorSave( @LoginUser SessionUser sessionUser) {

        // 로그인한 유저의 멘토 설정 가져오기
        if ( sessionUser != null  ){
            return "user/mentor-save";
        }
        // 나중에 에러 페이지로 변경
        return "null";
    }

    @GetMapping("/mentor")
    public String getMentor( Model model, @LoginUser SessionUser sessionUser) {

        Optional<Mentor> entity = null;
        String url = "user/mentor";

        // 로그인한 유저의 멘토 설정 가져오기
        if ( sessionUser != null  ){

            url = "user/mentor-save";

            // 로그인한 유저의 멘토
            String userEmail = sessionUser.getEmail();;
            User user = userService.getUserByEmail(sessionUser.getEmail());

            // 멘토 설정이 있을 경우
            if ( user.getMentor() != null ) {
                // 멘토
                Mentor mentor = user.getMentor();
                MentorResponseDto mentorResponseDto = MentorResponseDto.from(mentor);

                // 멘토시간 설정
                List<MentorTime> mentorTimeEntityList = user.getMentor().getMentorTimes();
                List<MentoringTimeReponseDto> mentorTimeList = new ArrayList<>();

                for ( MentorTime mentorTime : mentorTimeEntityList ) {
                    mentorTimeList.add(new MentoringTimeReponseDto(mentorTime));
                }

                model.addAttribute("mentor", mentorResponseDto );
                model.addAttribute("mentorTimeList", mentorTimeList );

                // url 변경
                url= "user/mentor";
            }

        }
        return url;
    }

    @GetMapping("/mentor/update")
    public String mentorUpdate( Model model, @LoginUser SessionUser sessionUser) {

        // 로그인한 유저의 멘토 설정 가져오기
        if ( sessionUser != null  ){
            String userEmail = sessionUser.getEmail();;
            User user = userService.getUserByEmail(sessionUser.getEmail());

            if ( user.getMentor() != null ) {

                List<MentorTime> mentorTimeEntityList = user.getMentor().getMentorTimes();

                List<MentoringTimeReponseDto> mentorTimeList = new ArrayList<>();

                for ( MentorTime mentorTime : mentorTimeEntityList ) {
                    mentorTimeList.add(new MentoringTimeReponseDto(mentorTime));
                }

                model.addAttribute("mentor", user.getMentor() );
                model.addAttribute("mentorTimeList", mentorTimeList );
                }
            }

        return "user/mentor-update";
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String mypage( Model model, @LoginUser SessionUser sessionUser) {

        // 로그인한 사용자의 정보 가져오기
        if ( sessionUser != null  ){
            User user = userService.getUserByEmail(sessionUser.getEmail());
            model.addAttribute("user", user);
        }

        return "user/mypage";
    }

    @GetMapping("/mypage/update")
    public String mypageUpdate( Model model, @LoginUser SessionUser sessionUser) {

        // 로그인한 사용자의 정보 가져오기
        if ( sessionUser != null  ){
            User user = userService.getUserByEmail(sessionUser.getEmail());
            model.addAttribute("user", user);
        }

        return "user/mypage-update";
    }

    @GetMapping("/mypage/email")
    public String email( @LoginUser SessionUser sessionUser) {
        return "user/email";
    }

    // 멘토 조회
    @GetMapping("/mentor/{id}")
    public String indexMentoring( Model model, @PathVariable("id") Long id, @LoginUser SessionUser sessionUser) {

        MentorIndexResponse responseDto = mentorService.findById(id)
                        .map(MentorIndexResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 멘토가 없습니다. id=" + id));

        model.addAttribute("mentor", responseDto);

        return "mentor/index";
    }

    // 내 멘토링 조회
    @GetMapping("/user/mentoring")
    public String mentoring(@PageableDefault(page = 1) Pageable pageable, Model model, @RequestParam(value = "state", defaultValue = "2") int state, @LoginUser SessionUser sessionUser) {

        User user = userService.getUserByEmail(sessionUser.getEmail());
        Page<MentoringListResponse> mentoriingListPageDto = mentoringService.getMyMentoring( pageable, user.getId(), state );

        int blockLimit = 5; // page 개수 설정
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), mentoriingListPageDto.getTotalPages());

        model.addAttribute("mentoringList", mentoriingListPageDto.getContent());
        model.addAttribute("mentoringPages", mentoriingListPageDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "user/mentoring";
    }

    @GetMapping("/mentoring/{id}")
    public String saveMentoring( Model model, @PathVariable("id") Long id, @LoginUser SessionUser sessionUser) {
        model.addAttribute("mentorId", id);
        return "mentoring/save";
    }


    @GetMapping("/error/login")
    public String getLoginError( ) {
        return "/error/longin-error";
    }

}

