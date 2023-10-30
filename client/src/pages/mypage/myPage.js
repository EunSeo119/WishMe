import React, { useState, useEffect } from "react";
import style from "./myPage.module.css";
import axios from "axios";

const MyPage  = () => {

    const [deskName, setDeskName] = useState("");
    const [schoolName, setSchoolName] = useState("");

    useEffect(() => {
        const AccessToken = localStorage.getItem("AccessToken");
        axios({
          method: "get",
          url: `http://localhost:8080/api/users`,
          headers: {
            Authorization: `Bearer ${AccessToken}`,
          },
        })
          .then((response) => {
            const data = response.data;
            console.log(data);
            setDeskName(data.data.userNickname);
            setSchoolName(data.data.schoolName);
          })
          .catch((error) => {
            console.error("API 요청 중 오류 발생:", error);
          });
      }, []);

    return (
        <div>
            <div className={style.title}>
                {deskName}님의<br/>
                마이페이지
            </div>
        </div>
    );
}

export default MyPage;