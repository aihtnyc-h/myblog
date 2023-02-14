package com.example.myblog.service;

import com.example.myblog.dto.LoginRequestDto;
import com.example.myblog.dto.SignupRequestDto;
import com.example.myblog.entity.User;
import com.example.myblog.entity.UserRoleEnum;
import com.example.myblog.jwt.JwtUtil;
import com.example.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        System.out.println(username);
//        if (username.length() < 4 || username.length() > 10){
//            throw new RuntimeException("아이디를 조건에 맞게 입력해주세요.");
//        }
//        if (!username.matches("^[0-9|a-z]*$")){
//            throw new RuntimeException("아이디를 조건에 맞게 입력해주세요.");
//        }
        String password = signupRequestDto.getPassword();
        System.out.println(password);
//        if (password.length() < 8 || password.length() > 15){
//            throw new RuntimeException("비밀번호를 조건에 맞게 입력해주세요.");
//        }
//        if (!password.matches("^[0-9|a-z|A-Z]*$")){
//            throw new RuntimeException("비밀번호를 조건에 맞게 입력해주세요.");
//        }

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()){
            throw  new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username,password,role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse reponse) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );


        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        reponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

    }
}



//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
//
//    // 가입하기 조건
//    // userName 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로
//    // password 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
//    @Transactional
//    public void signup(SignupRequestDto signupRequestDto) {
//        String username = signupRequestDto.getUsername();
////        System.out.println(userName);
////        if (userName.length() < 4 || userName.length() > 10) {
////            throw new RuntimeException("아이디가 짧거나 길어요");
////        }
////        if (!userName.matches("^[0-9|a-z]*$")) {
////            throw new RuntimeException("아이디는 영어 소문자나 숫자만 입력해 주세요.");
////        }
//        String password = signupRequestDto.getPassword();
////        System.out.println(password);
////        if (password.length() < 8 || password.length() > 15) {
////            throw new RuntimeException("비밀번호가 짧거나 길어요");
////        }
////        if (!password.matches("^[0-9|a-z|A-Z]*$")) {
////            throw new RuntimeException("비밀번호는 영어 소문자/대문자나 숫자만 입력해 주세요.");
////        }
//
////        String email = signupRequestDto.getEmail();
//
//        // 회원 중복 확인
//        Optional<User> found = userRepository.findByUserName(username);
//        if (found.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
//        // 사용자 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }
//
//        User user = new User(username, password, role);  //이메일이 왜 안들어갔는지 찾기  emial 생략함
//        userRepository.save(user);
//    }
//
//    @Transactional(readOnly = true)
//    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        String username = loginRequestDto.getUsername();
//        String password = loginRequestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUserName(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//        // 비밀번호 확인
//        if(!user.getPassword().equals(password)){
//            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserName(), user.getRole()));
//    }
//}

//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
//
//    @Transactional
//    public void signup(SignupRequestDto signupRequestDto) {
//        String username = signupRequestDto.getUsername();
//        String password = signupRequestDto.getPassword();
//
//        // 회원 중복 확인
//        Optional<User> found = userRepository.findByUsername(username);
//        if (found.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
//        // 사용자 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }
//
//        User user = new User(username, password, role);
//        userRepository.save(user);
//    }
//
//    @Transactional(readOnly = true)
//    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        String username = loginRequestDto.getUsername();
//        String password = loginRequestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//        // 비밀번호 확인
//        if(!user.getPassword().equals(password)){
//            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
//    }
//}