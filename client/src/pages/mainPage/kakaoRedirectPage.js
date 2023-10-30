import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const KakaoRedirectPage = () => {

    const navigate = useNavigate();
    const code = new URL(document.location.toString()).searchParams.get('code');

    useEffect(() => {
        axios.get('http://localhost:8080/api/users/login?code=' + code)
        .then((res) => {
            console.log(res.data);
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
