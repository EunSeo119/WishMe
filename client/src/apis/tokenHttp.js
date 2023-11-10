import axios from "axios";
import jwt_decode from "jwt-decode";
import dayjs from "dayjs";


const tokenHttp = axios.create({
    header: {
        "Content-Type": "application/json",
    },
});

tokenHttp.interceptors.request.use(async (req) => {
    const accessToken = localStorage.getItem("AccessToken");
    if (!accessToken) {
        return req
    }
    const user = jwt_decode(accessToken);
    const isExpired = dayjs().diff(dayjs.unix(user.exp)) < 1;

    // access token 이 만료되지 않았다면 access-token 을 넣어 요청 실행
    if (isExpired) {
        return req;
    }
    else {
        localStorage.removeItem("AccessToken");
        localStorage.removeItem("RefreshToken");
    }
});

export default tokenHttp;