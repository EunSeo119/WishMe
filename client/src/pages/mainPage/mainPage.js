import styleMain from "./mainPage.module.css";
import "../../fonts/font.css";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;
    const navigate = useNavigate();
    const REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY;
    const REDIRECT_URI = `${SERVER_URL}/kakao/callback`;
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

    const kakaoLogin = () => {
        window.location.href = kakaoURL;
    };

    return (
        <div className={styleMain.bg}>
            <div className={styleMain.topbox}>
                <div className={styleMain.title}>
                    Wish Me
                    <img
                        src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/clover.png"
                        width="85px"
                        crossOrigin="anonymous"
                    />
                </div>
                <div className={styleMain.subTitle}>나의 행운을 빌어줘</div>
                <div className={styleMain.desc}>
                    개인 책상에 온 편지는 <b>11월 11일</b>부터 확인 가능해요! <br/>
                    편지 Open 후, 답장도 주고 받을 수 있어요. <br/>
                    학교 편지는 항상 Open 되어 있어요 :)
                </div>
            </div>
            <div className={styleMain.bottombox}>
                <div className={styleMain.kakao} onClick={kakaoLogin}>
                    <img
                        src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/kakao_login_medium_wide.png"
                        crossOrigin="anonymous"
                    ></img>
                </div>
                <div
                    className={styleMain.nologin}
                    onClick={() => navigate(`/searchSchool`)}
                >
                    로그인하지 않고 학교 응원 구경하기
                </div>
            </div>
        </div>
    );
};

export default MainPage;
