import styleMain from "./mainPage.module.css";
import "../../fonts/font.css";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";

const MainPageUuid = () => {
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;
    const { deskUuid } = useParams();
    const navigate = useNavigate();
    const REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY;
    const REDIRECT_URI = `${SERVER_URL}/kakao/callback`;
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

    const kakaoLogin = () => {
        localStorage.setItem('LinkUuid', deskUuid)
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
            </div>
            <div className={styleMain.bottombox}>
                <div className={styleMain.kakao} onClick={kakaoLogin}>
                    <img
                        src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/kakao_login_medium_wide.png"
                        crossOrigin="anonymous"
                    ></img>
                </div>
                {/* <div
                    className={styleMain.nologin}
                    onClick={() => navigate(`/searchSchool`)}
                >
                    로그인하지 않고 이용하기
                </div> */}
            </div>
        </div>
    );
};

export default MainPageUuid;
