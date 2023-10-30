import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styleMain from "./mainPage.module.css"

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');

    useEffect(() => {
        axios.get('http://localhost:8080/api/users/login?code=' + code)
<<<<<<< HEAD
        .then((res) => {
            localStorage.setItem('AccessToken', res.data.data.token)
            localStorage.setItem('myUuid', res.data.data.uuid)
            localStorage.setItem('deskUuid', res.data.data.uuid);
            navigate(`/desk/${res.data.data.uuid}`);
        });
=======
            .then((res) => {
                localStorage.setItem('AccessToken', res.data.data.token)
                // localStorage.setItem('deskUuid', res.data.data.uuid)
                // localStorage.setItem('deskUserSeq', res.data.data.userSeq)
                // navigate(`/desk`);
                navigate(`/desk/${res.data.data.uuid}`);
            });
>>>>>>> b8f045ccc4f68d709be70f7f6ec3ce7f61d7bada
    }, []);

    return (
        <div className={styleMain.loadingPage}>
            로그인 중 ...
        </div>
    );
};

export default KakaoRedirectPage;
