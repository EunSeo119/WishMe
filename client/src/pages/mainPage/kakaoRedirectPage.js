import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styleMain from "./mainPage.module.css"

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');

    useEffect(() => {
        axios.get('http://localhost:8080/api/users/login?code=' + code)
        .then((res) => {
            localStorage.setItem('Accesstoken', res.data.data.token)
            localStorage.setItem('myUuid', res.data.data.uuid)
            localStorage.setItem('deskUuid', res.data.data.uuid);
            navigate(`/desk/${res.data.data.uuid}`);
        });
    }, []);

    return (
        <div className={styleMain.loadingPage}>
            로그인 중 ...
        </div>
    );
};

export default KakaoRedirectPage;
