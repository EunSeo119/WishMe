import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styleMain from "./mainPage.module.css"

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');
    const USER_SERVER = process.env.REACT_APP_USER_SERVER;

    useEffect(() => {
        axios.get(`${USER_SERVER}/api/users/login?code=${code}`)
            .then((res) => {
                localStorage.setItem('AccessToken', res.data.data.token);
                localStorage.setItem('RefreshToken', res.data.data.refresh_token)
                const linkUuid = localStorage.getItem('LinkUuid')
                localStorage.removeItem("LinkUuid")
                if(linkUuid == null){
                    navigate(`/desk/${res.data.data.uuid}`);
                }
                else{
                    navigate(`/desk/${linkUuid}`);
                }
                
            });
    }, []);

    return (
        <div className={styleMain.loadingPage}>
            로그인 중 ...
        </div>
    );
};

export default KakaoRedirectPage;
