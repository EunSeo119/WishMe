import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');

    useEffect(() => {
        axios.get('http://localhost:8080/api/users/login?code=' + code)
            .then((res) => {
                localStorage.setItem('AccessToken', res.data.data.token)
                // localStorage.setItem('deskUuid', res.data.data.uuid)
                // localStorage.setItem('deskUserSeq', res.data.data.userSeq)
                // navigate(`/desk`);
                navigate(`/desk/${res.data.data.uuid}`);
            });
    }, []);

    return (
        <div>
            로그인 중 ...
        </div>
    );
};

export default KakaoRedirectPage;
