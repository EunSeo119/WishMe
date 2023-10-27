import styleMain from "./mainPage.module.css"
import '../../fonts/font.css';

const MainPage = () => {

    return (
        <div className={styleMain.bg}>
            <div className={styleMain.topbox}>
                <div className={styleMain.title}>
                    Wish Me
                    <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/clover.png" width="85px" />
                </div>
                <div className={styleMain.subTitle}>
                    나의 행운을 빌어줘
                </div>
            </div>
            <div className={styleMain.bottombox}>
                <div className={styleMain.kakao}>
                    <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/kakao_login_medium_wide.png"></img>
                </div>
                <div className={styleMain.nologin}>
                    로그인하지 않고 이용하기
                </div>
            </div>
        </div>
    );
};

export default MainPage;
