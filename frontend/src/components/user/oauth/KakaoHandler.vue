<template>
  <div>Oauth(Kakao)</div>
</template>
<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { kakaoLogin } from "@/api/login.js";

export default {
  name: "KakaoHandler",
  mounted() {
    const router = useRouter();
    const store = useStore();
    const urlParams = new URL(location.href).searchParams;
    const code = urlParams.get("code");
    // * 네이버 인가코드 로직 수정(2022.08.08, rhz)
    if (code) {
      console.log("카카오 인가코드 발급완료 : " + code);
      kakaoLogin(
        { code: code },
        (response) => {
          store.commit("userStore/SET_SOCIAL", "KAKAO");
          if (response.data.isExist) {
            //아이디가 있을 경우
            console.log("서버에 로그인 요청 : 성공");
            console.log("발급된 토큰 : " + response.data.accessToken);
            store.commit("userStore/SET_IS_LOGIN", true);
            store.commit("userStore/SET_TOKEN", response.data.accessToken);
            store.commit("userStore/SET_USERID", response.data.userId);
            store.commit("userStore/SET_NAME", response.data.name);
            router.push({
              name: "station-chat",
              params: {
                nextLink: "map",
                speech: `${response.data.name} 님, 어디로 가시나요?`,
              },
            });
          } else {
            //아이디가 없을 경우
            console.log("서버에 로그인 요청 : 실패 (-> 회원가입)");
            console.log("임시 oauthId : " + response.data.oauthId);
            store.commit("userStore/SET_OAUTH_ID", response.data.oauthId);
            router.push({ name: "login-signup", query: { social: "kakao" } });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    }
  },
};
</script>
