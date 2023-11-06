import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styleMain from "./mainPage.module.css"

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;

    useEffect(() => {
        axios.get(`${SERVER_URL}/api/users/login?code=${code}`)
            .then((res) => {
                // const token = {AccessToken: res.data.data.token, expires: new Date().getTime() + 60*1000*1000}
                // localStorage.setItem('AccessToken', JSON.stringify(token));
                localStorage.setItem('AccessToken', res.data.data.token);
                localStorage.setItem('RefreshToken', res.data.data.refresh_token)
                // localStorage.setItem('deskUuid', res.data.data.uuid)
                // localStorage.setItem('deskUserSeq', res.data.data.userSeq)
                // navigate(`/desk`);
                console.log(res.data); // debug
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
